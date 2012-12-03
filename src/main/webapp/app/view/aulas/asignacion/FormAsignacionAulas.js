Ext.define('HOR.view.aulas.asignacion.FormAsignacionAulas',
{
    extend : 'Ext.form.Panel',
    alias : 'widget.formAsignacionAulas',
    title : "Assignació d'aula a event",
    border : false,
    buttonAlign : 'center',

    items : [

    ],

    buttons : [
    {
        text : 'Guardar',
        handler : function()
        {
            console.log('Guardar');
        }
    },
    {
        text : 'Tancar',
        handler : function()
        {
            console.log(this.text);
        }
    } ],

    prueba : function()
    {
        console.log('Entramos');
    }

});