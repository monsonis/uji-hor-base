Ext.define('HOR.view.aulas.FiltroAsignacionAulas',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.filtroAsignacionAulas',

    border : false,
    padding : 5,
    closable : false,

    layout : 'anchor',

    items : [
    {
        xtype : 'combobox',
        fieldLabel : 'Centres',
        labelWidth : 65,
        store : 'StoreCentrosGestion',
        editable : false,
        displayField : 'nombre',
        valueField : 'id',
        name : 'centro',
        anchor : '100%',
        alias : 'widget.comboCentros'
    },
    {
        xtype : 'combobox',
        lastQuery : '',
        fieldLabel : '&nbsp;',
        labelSeparator : ' ',
        queryMode : 'local',
        fields : [ 'id', 'nombre' ],
        value: 'all',
        store :
        {
            fields : [ 'id', 'nombre' ],

            data : [
            {
                id : 'solo',
                nombre : 'Titulaciones sólo de este centro'
            },
            {
                id : 'todos',
                nombre : 'Todas las titulaciones'
            } ]
        },
        forceSelection:true,
        labelWidth : 65,
        editable : false,
        displayField : 'nombre',
        valueField : 'id',
        name : 'filtroEstudio',
        anchor : '40%',
        disabled : true,
        alias : 'widget.comboFiltroEstudios'
    },
    {
        xtype : 'combobox',
        lastQuery : '',
        fieldLabel : 'Estudis',
        labelWidth : 65,
        store : 'StoreEstudiosGestion',
        editable : false,
        displayField : 'nombre',
        valueField : 'id',
        name : 'estudio',
        anchor : '100%',
        disabled : true,
        alias : 'widget.comboEstudios'
    },
    {
        xtype : 'combobox',
        lastQuery : '',
        fieldLabel : 'Semestre:',
        labelWidth : 65,
        store : Ext.create('Ext.data.ArrayStore',
        {
            fields : [ 'index', 'name' ],
            data : [ [ '1', '1' ], [ '2', '2' ] ]
        }),
        editable : false,
        displayField : 'name',
        valueField : 'index',
        name : 'semestre',
        anchor : '20%',
        disabled : true,
        alias : 'widget.comboSemestre'
    } ]
});