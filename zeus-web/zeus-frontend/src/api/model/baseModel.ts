export class SearchPagingRequest {
  public pageNum: number = 1;
  public pageSize: number = 10;
  public sorts: string | null = null;
}

export class SearchResult<T> {
  public total: number = 0;
  public pageNum: number = 0;
  public pageSize: number = 10;
  public list: T[] | null = null;
}
