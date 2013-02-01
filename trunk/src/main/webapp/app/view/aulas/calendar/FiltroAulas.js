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
           }),
           displayField : 'name',
           valueField : 'index',
           name : 'semestre',
           width : 120,
           queryMode : 'local'
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
           store : 'StoreEdificios',
           displayField : 'nombre',
           valueField : 'nombre',
           name : 'edificio',
       },
       {
           fieldLabel : 'Tipus aula',
           store : 'StoreTiposAula',
           displayField : 'nombre',
           valueField : 'valor',
           name : 'tipoAula',
       },
       {
           fieldLabel : 'Planta',
           store : 'StorePlantasEdificio',
           displayField : 'nombre',
           valueField : 'valor',
           name : 'planta'
       }]       
   }]
});