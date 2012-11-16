Ext.define('Event.form.DetalleClases',
{
    extend : 'Ext.form.FieldSet',
    alias : 'widget.detalleClases',
    title : 'La classe tindrà docència els dies:',
    layout :
    {
        type : 'table',
        columns : 6
    },
    hidden : true,

    style :
    {
        marginTop : '20px'
    },

    items : [],

    actualizarDetalleClases : function(clases)
    {
        this.removeAll();

        for ( var i = 0; i < clases.length; i++)
        {
            if (clases[i].docencia == 'S')
            {
                var fecha = Ext.Date.parse(clases[i].fecha, "d\/m/\Y H:i:s");
                fecha = Ext.Date.format(fecha, "d/m/Y");

                var cls = 'form-label-date';

                if (clases[i].tipoDia == 'E')
                {
                    fecha = '*' + fecha;
                    cls = 'form-label-exams-date';
                }

                var display = new Ext.create('Ext.form.field.Display',
                {
                    value : fecha,
                    fieldCls : cls,
                });

                this.add(display);
            }
        }
    }
});