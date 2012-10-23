Ext.define('Event.form.field.RadioDate',
{
    extend : 'Ext.container.Container',
    alias : 'widget.radiodatefield',
    
    layout :
        {
        type : 'hbox'
        },

    value : '',
    boxLabel : '',
    dateFormat : 'n/j/Y',
    name : '',
    dateName : '',

    initComponent : function()
    {
        this.items = [
        {
            xtype : 'radio',
            name : this.name,
            value : this.value,
            boxLabel : this.boxLabel,
            listeners :
            {
                'change' :
                {
                    fn : function(field, newValue, oldValue)
                    {
                        if (field.checked)
                        {
                            this.fireEvent('radiodateselect');
                        }
                    },
                    scope : this
                }
            }
        },
        {
            xtype : 'datefield',
            name : this.dateName,
            format : this.dateFormat,
            margin : '0 0 0 5',
            listeners :
            {
                'focus' :
                {
                    fn : function()
                    {
                        this.down('radio').setValue(true);
                        this.fireEvent('radiodateselect');
                    },
                    scope : this
                }
            }
        } ];

        this.callParent(arguments);
    }
});