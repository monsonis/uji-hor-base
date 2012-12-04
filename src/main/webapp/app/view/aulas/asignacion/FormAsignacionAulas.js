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
        displayField : 'aula',
        valueField : 'id',
        anchor : '90%',
        style :
        {
            marginTop : '20px',
            marginBottom : '30px'
        }
    },
    {
        xtype : 'checkbox',
        boxLabel : 'Assignar a tots els subgrups',
        inputValue : true,
        checked : true,
        anchor : '90%',
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
            value : ''
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