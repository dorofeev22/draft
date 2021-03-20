export class SearchParams {

  constructor(offset: number, limit: number) {
    this.offset = offset;
    this.limit = limit;
  }

  offset: number;
  limit: number;

}