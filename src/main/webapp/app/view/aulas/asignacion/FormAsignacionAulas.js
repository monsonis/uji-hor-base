Ext.define('HOR.view.aulas.asignacion.FormAsignacionAulas',
{
    extend : 'Ext.form.Panel',
    alias : 'widget.formAsignacionAulas',
    title : "Assignació d'aula a event",
    labelWidthRightCol : 65,
    colWidthLeft : .6,
    colWidthRight : .4,
    bodyStyle : 'padding:20px 20px 10px;',
    border : false,
    buttonAlign : 'center',
    autoHeight : true,
    cls : 'ext-evt-edit-form',
    trackResetOnLoad : true,

    items : [
    {
        xtype : 'displayfield',
        fieldLabel : 'Event',
        name : 'event',
        anchor : '90%'
    },
    {
        xtype : 'hiddenfield',
        name : 'eventId'
    },
    {
        xtype : 'combobox',
        fieldLabel : 'Aula assignada',
        name : 'aulaPlanificacion',
        anchor : '90%',
        style :
        {
            marginTop : '20px',
        },
        store : 'StoreAulasAsignadas',
        displayField : 'nombreYPlazas',
        valueField : 'aulaId',
        queryMode : 'local',
        emptyText : 'Sense assignar...',
        editable : false
    },
    {
        xtype : 'button',
        name : 'borrarAsignacion',
        text : 'Desassignar aula',
        iconCls : 'application-delete',
        hidden : true,
        style :
        {
            marginLeft : '105px'
        }
    },
    {
        xtype : 'combobox',
        name : 'tipoAccion',
        fieldLabel : "Tipus d'acció",
        anchor : '90%',
        style :
        {
            marginTop : '30px'
        },
        valueField : 'id',
        displayField : 'tipo',
        store : Ext.create('Ext.data.ArrayStore',
        {
            fields : [ 'id', 'tipo' ],
            data : [ [ 'T', 'Assignar a totes les classes del subgrup' ], [ 'U', 'Assignar només a aquesta classe' ] ]
        }),
        value : 'T',
        editable : false
    },
    {
        xtype : 'fieldset',
        title : 'Assignatures comuns',
        style :
        {
            marginTop : '30px'
        },
        anchor : '90%',
        items : [
        {
            xtype : 'displayfield',
            name : 'comunes',
            value : '',
            hidden : true
        } ]
    } ],

    buttons : [
    {
        text : 'Guardar',
        name : 'save',
    },
    {
        text : 'Tancar',
        name : 'close',
    } ],

    setStartDate : function(dt)
    {
        this.startDate = dt;
        return this;
    },

    getStartDate : function()
    {
        return this.startDate || Extensible.Date.today();
    },

});