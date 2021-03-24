export function isEmpty(value: any): boolean {
  return value == null || value.length === 0;
}

export function valueOrDefault<T>(value: T, defaultValue: T): T {
  return isEmpty(value) ? defaultValue : value;
}
