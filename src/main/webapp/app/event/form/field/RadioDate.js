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
    radioName : '',
    dateName : '',

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
            xtype : 'datefield',
            value : this.value,
            name : this.dateName,
            format : this.format,
            altFormats : this.altFormats,
            margin : '0 0 0 5',
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
        } ];

        this.callParent(arguments);
    }
});