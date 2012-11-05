Ext.define('Event.form.field.DateRepeat',
{
    extend : 'Ext.container.Container',
    alias : 'widget.daterepeatfield',
    dateFormat : 'n/j/Y',
    layout : 'anchor',

    items : [
    {
        xtype : 'datefield',
        name : Extensible.calendar.data.EventMappings.StartDateRep.name,
        format : 'j/n/Y',
        fieldLabel : 'Comença el',
        startDay : 1,
    },
    {
        xtype : 'combobox',
        name : Extensible.calendar.data.EventMappings.RepetirCada.name,
        fieldLabel : 'Repetir cada',
        valueField : 'repeatValue',
        displayField : 'repeatName',
        mode: 'local',
        triggerAction: 'all',
        value: 'Setmana',
        store : Ext.create('Ext.data.ArrayStore',
        {
            fields : [ 'repeatValue', 'repeatName' ],
            data : [ [ 1, 'Setmana' ], [ 2, '2 setmanes' ], [ 3, '3 setmanes' ], [ 4, '4 setmanes' ] ]
        }),
    },
    {
        xtype : 'radiogroup',
        name: 'grupoRadio',
        fieldLabel : 'Finalitza',
        vertical : true,
        columns : 1,
        defaults :
        {
            name : 'repetirHasta'
        },
        items : [
        {
            xtype : 'radio',
            name : Extensible.calendar.data.EventMappings.FechaFinRadio.name,
            inputValue : "F",
            boxLabel : 'Al final del semestre',
            checked : true
        },
        {
            xtype : 'radionumberfield',
            inputValue : "R",
            boxLabel : 'Després de ',
            endLabel : 'repeticions',
            radioName : Extensible.calendar.data.EventMappings.FechaFinRadio.name,
            numberName : Extensible.calendar.data.EventMappings.EndRepNumberComp.name,
            minValue : 1
        },
        {
            xtype : 'radiodatefield',
            inputValue : "D",
            boxLabel : 'El ',
            radioName : Extensible.calendar.data.EventMappings.FechaFinRadio.name,
            dateName : Extensible.calendar.data.EventMappings.EndDateRepComp.name,
            dateFormat : 'j/n/Y',
            listeners :
            {
                'radiodateselect' : function()
                {
                    this.up('daterepeatfield').down('radio[name=' + Extensible.calendar.data.EventMappings.NoEnd.name + ']').setValue(false);
                    this.up('daterepeatfield').down('radionumberfield radio').setValue(false);
                }

            }
        } ]
    }, ],

    initComponent : function()
    {
        this.callParent(arguments);
    },
    
    disableFields : function()
    {
        this.down('datefield[name=' + Extensible.calendar.data.EventMappings.StartDateRep.name + ']').disable();
        this.down('combobox[name=' + Extensible.calendar.data.EventMappings.RepetirCada.name + ']').disable();
        this.down('radiogroup').disable();
    },
    
    enableFields : function()
    {
        this.down('datefield[name=' + Extensible.calendar.data.EventMappings.StartDateRep.name + ']').enable();
        this.down('combobox[name=' + Extensible.calendar.data.EventMappings.RepetirCada.name + ']').enable();
        this.down('radiogroup').enable();
    }
});