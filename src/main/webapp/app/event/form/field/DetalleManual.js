Ext.define('Event.form.field.DetalleManual',
{
    extend : 'Ext.form.Panel',
    alias : 'widget.detalleManual',
    hidden : true,
    
    name : '',
    nameCheckbox : '',
    seleccionadas : false,
    
    items:[{
        xtype: 'checkboxgroup',
        name : 'grupoDetalleManualFechas',
        columns: 6,
        vertical: true,
        items: [
        ]
    }],
    
    initComponent : function()
    {
        this.callParent(arguments);
        
    },
   
    addPosiblesFechas : function(clases)
    {
        this.items.items[0].removeAll();
        
        this.seleccionadas = false;

        for (var i=0; i < clases.length; i++)
        {
            var fecha = Ext.Date.parse(clases[i].fecha, "d\/m/\Y H:i:s");
            var fechaStr = Ext.Date.format(fecha, "d/m/Y");
            
            var checkbox = Ext.create('Ext.form.field.Checkbox', {
              inputValue : clases[i].fecha,
              name : this.nameCheckbox,
              boxLabel : fechaStr
            });
            
            this.items.items[0].add(checkbox);
            
            if (clases[i].impartirClase)
            {
                this.seleccionadas = true;
            }
        }
    },
    
    checkBoxes : function()
    {
        var checkboxes = this.down('checkboxgroup').items.items;
        
        for (var i=0; i < checkboxes.length; i++)
        {
            if (checkboxes[i].getValue() || !this.seleccionadas)
            {
                checkboxes[i].setValue(true);
            }
        }
    }
    
});