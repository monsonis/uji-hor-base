Ext.define('HOR.view.commons.FiltroGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.filtroGrupos',

    border : false,
    padding : 5,
    closable : false,

    items : [
    {
        xtype : 'combobox',
        fieldLabel : 'Titulació',
        store : 'StoreEstudios',
        editable : false,
        displayField : 'nombre',
        valueField : 'id',
        name : 'estudio',
    },
    {
        xtype : 'combobox',
        fieldLabel : 'Curso',
        store : 'StoreCursos',
        editable : false,
        displayField : 'curso',
        valueField : 'curso',
        lastQuery : '',
        name : 'curso'
    },
    {
        xtype : 'combobox',
        fieldLabel : 'Semestre',
        store : 'StoreSemestres',
        editable : false,
        displayField : 'semestre',
        valueField : 'semestre',
        lastQuery : '',
        name : 'semestre'
    },
    {
        xtype : 'combobox',
        fieldLabel : 'Grupo',
        store : 'StoreGrupos',
        editable : false,
        displayField : 'grupo',
        valueField : 'grupo',
        lastQuery : '',
        name : 'grupo'
    } ]
});