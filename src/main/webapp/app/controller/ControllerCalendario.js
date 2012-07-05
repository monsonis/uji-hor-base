Ext.define('HOR.controller.ControllerCalendario', {
   extend : 'Ext.app.Controller',
   stores : [ 'StoreCalendarios' ],
   model : [ 'Calendario' ],
   views : [ 'horarios.PanelCalendario' ],

   init : function() {
   }  
});