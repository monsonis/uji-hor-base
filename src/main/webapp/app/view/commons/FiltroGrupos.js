Ext.define('HOR.view.commons.FiltroGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.filtroGrupos',

    border : false,
    padding : 5,
    closable : false,

    layout: 'anchor',
    
    items : [
    {
        xtype : 'combobox',
        fieldLabel : 'Titulació',
        store : 'StoreEstudios',
        editable : false,
        displayField : 'nombre',
        valueField : 'id',
        name : 'estudio',
        anchor: '50%'
    },
    {
        xtype : 'combobox',
        fieldLabel : 'Curso',
        store : 'StoreCursos',
        editable : false,
        displayField : 'curso',
        valueField : 'curso',
        lastQuery : '',
        name : 'curso',
        anchor: '15%'
    },
    {
        xtype : 'combobox',
        fieldLabel : 'Semestre',
        store : 'StoreSemestres',
        editable : false,
        displayField : 'semestre',
        valueField : 'semestre',
        lastQuery : '',
        name : 'semestre',
        anchor: '15%'
    },
    {
        xtype : 'combobox',
        fieldLabel : 'Grupo',
        store : 'StoreGrupos',
        editable : false,
        displayField : 'grupo',
        valueField : 'grupo',
        lastQuery : '',
        name : 'grupo',
        anchor: '15%'
    } ]
});