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
    radioName : '',
    numberName : '',

    initComponent : function()
    {
        this.items = [
        {
            xtype : 'radio',
            name : this.radioName,
            inputValue : this.inputValue,
            boxLabel : this.boxLabel
        },
        {
            xtype : 'numberfield',
            margin : '0 5 0 5',
            value : this.value,
            minValue : this.minValue,
            name : this.numberName,
            listeners :
            {
                'focus' :
                {
                    fn : function()
                    {
                        this.down('radio').setValue(true);
                    },
                    scope : this
                }
            }
        },
        {
            xtype : 'label',
            name : 'endLabel',
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
    },

    disableEndLabel : function()
    {
        this.down('label[name=endLabel]').addCls('form-disabled-label');
    },

    enableEndLabel : function()
    {
        this.down('label[name=endLabel]').removeCls('form-disabled-label');
    }
});