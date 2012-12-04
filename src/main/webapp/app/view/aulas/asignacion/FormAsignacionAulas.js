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
        displayField : 'nombre',
        valueField : 'id',
        queryMode : 'local',
        emptyText : 'Sense assignar...'
    },
    {
        xtype : 'button',
        name : 'borrarAsignacion',
        text : 'Desassignar aula',
        iconCls : 'application-delete',
        hidden : true
    },
    {
        xtype : 'checkbox',
        boxLabel : 'Assignar a tots els subgrups',
        inputValue : true,
        checked : true,
        anchor : '90%',
        style :
        {
            marginTop : '30px'
        }
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
        handler : function()
        {
            // console.log('Guardar');
        }
    },
    {
        text : 'Tancar',
        name : 'close',
        handler : function()
        {
            // console.log(this.text);
        }
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