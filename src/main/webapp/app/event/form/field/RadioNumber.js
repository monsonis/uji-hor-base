Ext.define('Event.form.field.RadioNumber',
{
    extend : 'Ext.container.Container',
    alias : 'widget.radionumberfield',

    layout :
    {
        type : 'hbox'
    },

    value : '',
    boxLabel : '',
    endLabel : '',
    minValue : 0,
    name : '',
    numberName : '',

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
                            this.fireEvent('radionumberselect');
                        }
                    },
                    scope : this
                }
            }
        },
        {
            xtype : 'numberfield',
            margin : '0 5 0 5',
            minValue : this.minValue,
            name : this.numberName,
            listeners :
            {
                'focus' :
                {
                    fn : function()
                    {
                        this.down('radio').setValue(true);
                        this.fireEvent('radionumberselect');
                    },
                    scope : this
                }
            }
        },
        {
            xtype : 'label',
            text : this.endLabel,
            margins :
            {
                top : 4,
                right : 5,
                bottom : 0,
                left : 0
            }
        } ];

        this.callParent(arguments);
    }
});