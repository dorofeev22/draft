import {isEmpty} from '../utils/object-utils';

export class Column {
    constructor(columnType: ColumnType, header: string) {
        this.type = columnType;
        this.header = header;
    }
    type: ColumnType;
    header: string;
}

export enum ColumnType {
    data = 'DATA', action = 'ACTION'
}
export class DataColumn extends Column {
    constructor(field: string, header: string, filterType: FilterType) {
        super(ColumnType.data, header);
        this.field = field;
        this.header = header;
        this.filterType = filterType;
    }
    field: string;
    filterType: FilterType;
}

export class ActionColumn extends Column {
    constructor(header: string, actions: Action[]) {
        super(ColumnType.action, header);
        this.actions = actions;
    }
    actions: Action[];
}

export enum FilterType {
    none,
    input = 'INPUT',
    option = 'OPTION',
    date = 'DATE'
}

export class Action {
    constructor(actionType: ActionType, route?: string) {
        this.actionType = actionType;
        this.route = route;
    }
    actionType: ActionType;
    route: string;
}

export enum ActionType {
    navigate = 'NAVIGATE',
    edit = 'EDIT',
    delete = 'DELETE'
}

export function createColumn(field: string, header: string, filtering?: boolean): Column {
    return new DataColumn(field, header, filterTypeOrNone(filtering, FilterType.input));
}

export function createDateColumn(field: string, header: string, filtering?: boolean): Column {
    return new DataColumn(field, header,  filterTypeOrNone(filtering, FilterType.date));
}

export function createActionColumn(actions: Action[]): ActionColumn {
    return new ActionColumn('', actions);
}

export function createEditAction(route: string): Action {
    return new Action(ActionType.edit, route);
}

export function createDeleteAction(): Action {
    return new Action(ActionType.delete);
}

function filterTypeOrNone(filtering: boolean, filterType: FilterType): FilterType {
    return isEmpty(filtering) ? FilterType.none : filterType;
}