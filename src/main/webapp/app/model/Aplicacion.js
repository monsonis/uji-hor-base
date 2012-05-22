Ext.define('APA.model.Aplicacion', {
   extend : 'Ext.data.Model',

   fields : [ 'id', 'nombre', 'url', 'codigo' ],
   
   validations : [ {
      type : 'length',
      field : 'nombre',
      min : 1
   }, {
      type : 'length',
      field : 'url',
      min : 1
   }, {
      type : 'length',
      field : 'codigo',
      min : 1
   } ]
});
