export class ApiError {
  status: number;
  statusText: string;
  error: ErrorResponse;
  message: string;
}

export class ErrorResponse {
  errorCode: string;
  errorMessage: string;
}