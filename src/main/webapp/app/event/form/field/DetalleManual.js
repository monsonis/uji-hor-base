Ext.define('Event.form.field.DetalleManual',
{
    extend : 'Ext.form.Panel',
    alias : 'widget.detalleManual',
    hidden : true,

    name : '',
    nameCheckbox : '',
    nameHidden : '',
    seleccionadas : false,
    textButton : '',

    items : [
    {
        xtype : 'checkboxgroup',
        name : 'grupoDetalleManualFechas',
        columns : 6,
        items : [],
    },
    {
        xtype : 'hidden',
        name : this.nameHidden
    } ],

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

            var cls = 'form-label-date-manual';
            var disabled = false;

            if (clases[i].tipoDia == 'E')
            {
                fecha = "[*] " + fecha;
                cls = 'form-label-exam-date-manual';
            }
            else if (clases[i].tipoDia == 'F')
            {
                disabled = true;
            }

            var checkbox = Ext.create('Ext.form.field.Checkbox',
            {
                inputValue : clases[i].fecha,
                name : this.nameCheckbox,
                boxLabel : fecha,
                checked : checked,
                cls : cls,
                disabled : disabled
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
    },

    numer_seleccionados : function()
    {
        var checkboxes = this.down('checkboxgroup').items.items;
        var nselecs = 0;

        for ( var i = 0; i < checkboxes.length; i++)
        {
            if (checkboxes[i].getValue())
            {
                nselecs = nselecs + 1;
            }
        }
        return nselecs;
    },

    uncheckAllBoxes : function()
    {
        var checkboxes = this.down('checkboxgroup').items.items;

        for ( var i = 0; i < checkboxes.length; i++)
        {
            if (checkboxes[i].getValue())
            {
                checkboxes[i].setValue(false);
            }
        }
    }

});