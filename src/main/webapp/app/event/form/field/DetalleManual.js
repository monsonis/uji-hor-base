Ext.define('Event.form.field.DetalleManual',
{
    extend : 'Ext.form.Panel',
    alias : 'widget.detalleManual',
    hidden : true,

    name : '',
    nameCheckbox : '',
    nameHidden : '',
    seleccionadas : false,

    items : [
    {
        xtype : 'checkboxgroup',
        name : 'grupoDetalleManualFechas',
        columns : 6,
        vertical : true,
        items : [],
    },
    {
        xtype : 'hidden',
        name : this.nameHidden
    },
    {
        xtype : 'displayfield',
        value : "*Període d'exàmens",
        style :
        {
            font: 'italic 11px tahoma,arial,verdana,sans-serif !important',
            marginLeft: '3px',
        }
    }],

    initComponent : function()
    {
        this.callParent(arguments);

    },

    addPosiblesFechas : function(clases)
    {
        this.items.items[0].removeAll();

        for ( var i = 0; i < clases.length; i++)
        {
            var fecha = Ext.Date.parse(clases[i].fecha, "d\/m/\Y H:i:s");
            fecha = Ext.Date.format(fecha, "d/m/Y");

            var checked = false;
            if (clases[i].docencia == 'S')
            {
                checked = true;
            }
            
            var value = clases[i].fecha;
            
            if (clases[i].tipo_docente == 'E')
            {
                value = "*" + value;
            }

            var checkbox = Ext.create('Ext.form.field.Checkbox',
            {
                inputValue : clases[i].fecha,
                name : this.nameCheckbox,
                boxLabel : fecha,
                checked : checked
            });

            this.items.items[0].add(checkbox);
        }
    },

    checkBoxes : function()
    {
        var checkboxes = this.down('checkboxgroup').items.items;

        for ( var i = 0; i < checkboxes.length; i++)
        {
            if (checkboxes[i].getValue())
            {
                checkboxes[i].setValue(true);
            }
        }
    }

});