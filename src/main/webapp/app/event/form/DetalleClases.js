Ext.define('Event.form.DetalleClases',
{
    extend : 'Ext.form.FieldSet',
    title : 'La classe tindrà docència els dies:',
    layout: {
        type: 'table',
        columns: 6
    },
    
    style :
    {
        marginTop: '20px'
    },
    
    items : [
       {
          html: 'textfield',
          style :
          {
              border : '0',
              paddingRight: '20px'
          }
       } ,
       {
           html: 'textfield',
       }  ,
       { xtype: 'tbtext', text: 'Sample Text Item' }  
             
    ],
    
    actualizarDetalleClases : function(clases)
    {
        for (var i=0; i < clases.length; i++)
        {
            //this.add
        }
    }
});