Ext.define('Event.form.DetalleClases',
{
    extend : 'Ext.form.FieldSet',
    alias : 'widget.detalleClases',
    title : 'La classe tindrà docència els dies:',
    layout: {
        type: 'table',
        columns: 6
    },
    hidden : true,
    
    style :
    {
        marginTop: '20px'
    },
    
    items : [ ],
    
    actualizarDetalleClases : function(clases)
    {
        this.removeAll();
        
        for (var i=0; i < clases.length; i++)
        {
            var display = new Ext.create('Ext.form.field.Display',
            {
                value : clases[i],
                style :
                {
                    marginRight : '50px'
                }
            });
            
            this.add(display);
        }
    }
});