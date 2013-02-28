Ext.define('HOR.controller.ControllerCalendarioCircuitos',
{
    extend : 'Ext.app.Controller',
    //stores : [ 'StoreCalendariosCircuitos', 'StoreEventosCircuito' ],
    refs : [
    {
        selector : 'panelCircuitos filtroCircuitos',
        ref : 'filtroCircuitos'
    },
    {
        selector : 'panelCircuitos selectorCircuitos',
        ref : 'selectorCircuitos'
    },
    {
        selector : 'panelCircuitos panelCalendarioCircuito',
        ref : 'panelCalendarioCircuito'
    },
    {
        selector : 'panelCircuitos panelCalendarioDetalleCircuito',
        ref : 'panelCalendarioDetalleCircuito'
    },
    {
        selector : 'panelCircuitos selectorCalendariosCircuitos',
        ref : 'selectorCalendariosCircuitos'
    },
    {
        selector : 'panelCircuitos filtroCircuitos combobox[name=grupo]',
        ref : 'comboGrupos'
    },
    {
        selector : 'filtroCircuitos button[name=calendarioCircuitosDetalle]',
        ref : 'botonCalendarioDetalle'
    },
    {
        selector : 'filtroCircuitos button[name=calendarioCircuitosGenerica]',
        ref : 'botonCalendarioGenerica'
    },
    {
        selector : 'filtroCircuitos button[name=imprimir]',
        ref : 'botonImprimir'
    } ],

    init : function()
    {        
        this.control(
        {
            'panelCircuitos filtroCircuitos combobox[name=grupo]' : 
            {
                blur : function()
                {
                    if (this.getComboGrupos().getValue() != '')
                    {
                        // Refrescaríamos el calendario correspondiente
                        
                        this.showBotonesCalendario();
                    }
                }
            }
        });
    },
    
    showBotonesCalendario : function()
    {
        this.getBotonCalendarioDetalle().show();
        this.getBotonCalendarioGenerica().show();
        this.getBotonImprimir().show();
    },

});