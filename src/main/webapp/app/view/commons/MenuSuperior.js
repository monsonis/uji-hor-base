Ext.define('HOR.view.commons.MenuSuperior',
{
    extend : 'Ext.toolbar.Toolbar',
    alias : 'widget.menuSuperior',
    width : 200,
    menuVisible: false,
    items : [
    {
        xtype : 'splitbutton',
        alias : 'widget.splitbutton',
        text : 'Menú Principal',
        menu :
        {
            xtype : 'menu',
            titleCollapse: true,
            items : [
            {
                text : 'Gestió d\'horaris',
                action : 'gestion-horarios'
            },
            {
                text : 'Dates del curs acadèmic',
                action : 'curso-academico'
            },
            {
                text : 'Assignació d\'aules',
                action : 'asignacion-aulas'
            } ]
        },
        handler: function(button, event) {
            if (!this.menuVisible) {
                button.showMenu();
                this.menuVisible = true;
            } else {
                button.hideMenu();
                this.menuVisible = false;
            }
        }
    } ]
});
