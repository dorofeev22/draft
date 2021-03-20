export class Column {
    constructor(field: string, header: string, filterType: FilterType = FilterType.none) {
        this.field = field;
        this.header = header;
        this.filterType = filterType;
    }
    field: string;
    header: string;
    filterType: FilterType;
}

export enum FilterType {
    none,
    input = 'INPUT',
    option = 'OPTION',
    date = 'DATE'
}

export function createColumn(field: string, header: string): Column {
    return new Column(field, header);
}

export function createDateColumn(field: string, header: string): Column {
    return new Column(field, header, FilterType.date);
}