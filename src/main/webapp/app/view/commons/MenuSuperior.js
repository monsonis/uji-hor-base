Ext.define('HOR.view.commons.MenuSuperior',
{
    extend : 'Ext.toolbar.Toolbar',
    alias : 'widget.menuSuperior',
    width : 200,
    items : [
    {
        xtype : 'splitbutton',
        alias : 'widget.splitbutton',
        text : 'Menú Principal',
        menu :
        {
            xtype : 'menu',
            items : [
            {
                text : 'Gestió d\'horaris',
                action: 'gestion-horarios'
            },
            {
                text : 'Dates del curs acadèmic',
                action: 'curso-academico'
            }]
        }

    } ]
});
