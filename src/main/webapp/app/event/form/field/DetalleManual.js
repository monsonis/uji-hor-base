Ext.define('Event.form.field.DetalleManual',
{
    extend : 'Ext.form.Panel',
    alias : 'widget.detalleManual',
    hidden : true,
    
    name : '',
    nameCheckbox : '',
    
    items:[{
        xtype: 'checkboxgroup',
        name : this.name,
        columns: 6,
        vertical: true,
        items: [
        ]
    }],
    
    initComponent : function()
    {
        this.callParent(arguments);
        
    },
   
    addPosiblesFechas : function(data, values)
    {
        this.items.items[0].removeAll();

        for (var i=0; i < data.length; i++)
        {
            var checkbox = Ext.create('Ext.form.field.Checkbox', {
              inputValue : data[i],
              name : this.nameCheckbox,
              boxLabel : values[i],
            });
            
            this.items.items[0].add(checkbox);
        }
    },
    
    checkAllBoxes : function()
    {
        var checkboxes = this.down('checkboxgroup').items.items;
        
        for (var i=0; i < checkboxes.length; i++)
        {
            checkboxes[i].setValue(true);
        }
    }
    
});