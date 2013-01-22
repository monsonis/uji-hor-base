Ext.define('HOR.view.aulas.calendar.FiltroAulas',
{
   extend : 'Ext.panel.Panel',
   alias : 'widget.filtroAulas',
   
   border : false,
   padding : 5,
   closable : false,
   layout : 'anchor',
   
   items : [
   {
       xtype : 'panel',
       border : 0,
       layout : 
       {
           type : 'hbox',
           align : 'stretch'
       },
       defaults :
       {
           xtype : 'combobox',
           editable : false,
           lastQuery : '',
           labelWidth : 75,
           labelAlign : 'left',
           margin : '0 20 0 0'
       },
       items : [
       {
           fieldLabel : 'Centre',
           store : 'StoreCentros',
           displayField : 'nombre',
           valueField : 'id',
           name : 'centro',
           width : 800,
       },
       {
           fieldLabel : 'Semestre',
           store : Ext.create('Ext.data.ArrayStore',
           {
               fields : [ 'index', 'name' ],
               data : [ [ '1', '1' ], [ '2', '2' ] ]
           }),
           displayField : 'name',
           valueField : 'index',
           name : 'semestre',
           alias : 'widget.comboSemestreCA',
           width : 120
       }]   
   },
   {
       xtype : 'panel',
       border : 0,
       anchor : '70%',
       layout : 
       {
           type : 'hbox',
           align : 'fit',
           padding : '5 0 0 0'
       },
       defaults :
       {
           xtype : 'combobox',
           editable : false,
           lastQuery : '',
           labelWidth : 75,
           labelAlign : 'left',
           margin : '0 20 0 0'
       },
       items : [
       {
           fieldLabel : 'Edifici',
           //store : 'StoreEdificios',
           displayField : 'nombre',
           valueField : 'id',
           name : 'edificio',
       },
       {
           fieldLabel : 'Tipus aula',
           //store : 'StoreTipoAulas',
           displayField : 'tipo',
           valueField : 'id',
           name : 'tipoAula',
       },
       {
           fieldLabel : 'Planta',
           //store : 'StorePlantas',
           displayField : 'planta',
           valueField : 'id',
           name : 'planta',
           width : 120
       }]       
   }]
});