Ext.define('Event.form.PanelInfo',
{
    extend : 'Ext.container.Container',
    alias : 'widget.panelinfo',
    style :
    {
        paddingTop : '10px',
        paddingBottom : '10px',
    },
    hidden : true,

    showAviso : function(msg)
    {
        this.update("<div style='color: #FF9900;'><img src='/hor/images/warning.png' style='margin-right: 5px;' />" + msg + "</div>");
        this.show();
    },

    dropAviso : function()
    {
        this.update("");
        this.hide();
    }
});