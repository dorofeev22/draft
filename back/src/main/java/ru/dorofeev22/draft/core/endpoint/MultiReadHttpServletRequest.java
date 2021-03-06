package ru.dorofeev22.draft.core.endpoint;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper  {
    
    private ByteArrayOutputStream cachedBytes;
    
    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null)
            cacheInputStream();
        
        return new CachedServletInputStream();
    }
    
    @Override
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
    
    private void cacheInputStream() throws IOException {
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }
    
    public class CachedServletInputStream extends ServletInputStream {
        
        private ByteArrayInputStream input;
        
        public CachedServletInputStream() {
            input = new ByteArrayInputStream(cachedBytes.toByteArray());
        }
    
        @Override
        public boolean isFinished() {
            return input.available() == 0;
        }
    
        @Override
        public boolean isReady() {
            return true;
        }
    
        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
    
        @Override
        public int read() throws IOException {
            return input.read();
        }
    }
}