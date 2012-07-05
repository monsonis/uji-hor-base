Ext.define('HOR.model.Calendario', {
   extend : 'Ext.data.Model',

   fields : [ 'id', 'title', 'color' ],
   
   validations : [ {
      type : 'length',
      field : 'title',
      min : 1
   }, {
      type : 'length',
      field : 'color',
      min : 1
   } ]
});
