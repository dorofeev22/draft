export class ApiError {
  status: number;
  error: ErrorResponse;
  message: string;
}

export class ErrorResponse {
  errorCode: string;
  errorMessage: string;
}