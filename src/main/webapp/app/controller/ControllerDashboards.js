Ext.define('APA.controller.ControllerDashboards', {
	extend : 'Ext.app.Controller',
	views : [ 'dashboard.PanelDashboard' ],

	init : function() {
		this.control({
			'viewport > treepanel' : {
			   itemclick : this.treeItemSelected
			},
		});
	},
	
	treeItemSelected : function(view, node, item, index, event, opts)
	{
	   view.up("viewport").addNewTab(node.data.id);
	}
});