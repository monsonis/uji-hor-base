Ext.define('HOR.view.dashboard.PanelDashboard', {
   extend : 'Ext.panel.Panel',
   alias : 'widget.dashboard',
   title : 'Dashboard',
   frame : true,   
   closable : false,
   layout : {
	   type : 'vbox',
	   width : '100%',
	   align: 'center',
   },
   
   items : [
            {
            	xtype : 'label',
         	    width : '100%',
            	text : 'Aplicació de Gestió d\'Horaris Acadèmics',
            	style : 'font-size : 30px; font-weight : bold; text-align : center; margin-top : 30px'
            },
            {
            	xtype : 'label',
         	    width : '100%',
            	text : 'Versió '+appversion,
            	style : 'font-size : 12px; text-align : center'
            }
            ]
});