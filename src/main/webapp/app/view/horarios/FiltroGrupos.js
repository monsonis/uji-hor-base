Ext.define('HOR.view.horarios.FiltroGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.filtroGrupos',

    border : false,
    padding : 5,
    closable : false,

    layout : 'anchor',

    items : [
    {
        xtype : 'combobox',
        fieldLabel : 'Titulació',
        labelWidth: 75,
        store : 'StoreEstudios',
        editable : false,
        displayField : 'nombre',
        valueField : 'id',
        name : 'estudio',
        anchor : '80%'
    },
    {
        xtype : 'panel',
        border : 0,
        anchor : '80%',
        layout :
        {
            type : 'hbox',
            align : 'fit'
        },
        defaults :
        {
            xtype : 'combobox',
            editable : false,
            lastQuery : '',
            width : 120,
            labelWidth: 75,
            labelAlign : 'left',
            margin : '0 20 0 0',
        },
        items : [
        {
            fieldLabel : 'Curso',
            store : 'StoreCursos',
            displayField : 'curso',
            valueField : 'curso',
            name : 'curso'
        },
        {
            fieldLabel : 'Semestre',
            store : 'StoreSemestres',
            displayField : 'semestre',
            valueField : 'semestre',
            name : 'semestre'
        },
        {
            fieldLabel : 'Grupo',
            store : 'StoreGrupos',
            displayField : 'grupo',
            valueField : 'grupo',
            name : 'grupo'
        },
        {
            margin : '0 0 0 0',
            name: 'intervaloHorario',
            xtype: 'button',
            hidden: true,
            width: '40',
            flex: 0,
            text : 'Interval horari...',
        } ]
    } ]
});