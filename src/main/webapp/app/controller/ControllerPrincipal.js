Ext.define('HOR.controller.ControllerPrincipal',
{
    extend : 'Ext.app.Controller',
    views : 'ApplicationViewport',
    refs : [
    {
        selector : 'applicationViewportHorarios',
        ref : 'viewportHorarios'

    } ],
    init : function()
    {
        this.control(
        {           
            'viewport > treepanel' :
            {
                itemclick : this.navigationTreeItemClick
            }
        });
    },
    
    navigationTreeItemClick : function (view, node, item, index, e) {
    	view.up("viewport").addNewTab(node.data.id);
    },
  

});