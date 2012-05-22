Ext.define('APA.model.Cuenta', {
   extend : 'Ext.data.Model',

   fields : [ 'id', 'personaId', 'cuenta' ],

   validations : [ {
      type : 'length',
      field : 'personaId',
      min : 1
   }, {
      type : 'length',
      field : 'cuenta',
      min : 1
   } ]
});
