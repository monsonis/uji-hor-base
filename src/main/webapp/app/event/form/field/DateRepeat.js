Ext.define('Event.form.field.DateRepeat',
{
    extend : 'Ext.container.Container',
    alias : 'widget.daterepeatfield',

    dateFormat : 'n/j/Y',

    layout : 'anchor',

    hidden : true,

    items : [
    {
        xtype : 'combobox',
        name : Extensible.calendar.data.EventMappings.RepetirCada.name,
        fieldLabel : 'Repetir cada',
        valueField : 'repeatValue',
        displayField : 'repeatName',
        store : Ext.create('Ext.data.ArrayStore',
        {
            fields : [ 'repeatValue', 'repeatName' ],
            data : [ [ 2, 'Setmanes alternes' ], [ 3, 'Cada 3 setmanes' ], [ 4, 'Cada 4 setmanes' ] ]
        }),
    },
    {
        xtype : 'datefield',
        name : Extensible.calendar.data.EventMappings.StartDateRep.name,
        format : 'j/n/Y',
        fieldLabel : 'Comença el',
        startDay : 1
    },
    {
        xtype : 'radiogroup',
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
            name : Extensible.calendar.data.EventMappings.NoEnd.name,
            value : 0,
            boxLabel : 'Segons semestre',
            checked : true,
            listeners :
            {
                'change' : function(field, newValue, oldValue)
                {
                    if (field.checked)
                    {
                        this.up('daterepeatfield').down('radionumberfield radio').setValue(false);
                        this.up('daterepeatfield').down('radiodatefield radio').setValue(false);
                    }
                }
            }
        },
        {
            xtype : 'radionumberfield',
            value : 1,
            boxLabel : 'Després de ',
            endLabel : 'repeticions',
            name : Extensible.calendar.data.EventMappings.EndRepNumber.name,
            numberName : Extensible.calendar.data.EventMappings.EndRepNumberComp.name,
            minValue : 1,
            listeners :
            {
                'radionumberselect' : function()
                {
                    this.up('daterepeatfield').down('radio[name=' + Extensible.calendar.data.EventMappings.NoEnd.name + ']').setValue(false);
                    this.up('daterepeatfield').down('radiodatefield radio').setValue(false);
                }

            }
        },
        {
            xtype : 'radiodatefield',
            value : 2,
            boxLabel : 'El ',
            name : Extensible.calendar.data.EventMappings.EndDateRep.name,
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
    }
});