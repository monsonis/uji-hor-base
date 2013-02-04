Ext.define('HOR.controller.ControllerFiltroAulasCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCentros', 'StoreEdificios', 'StoreTiposAula', 'StorePlantasEdificio'],
    refs : [
    {
        selector : 'filtroAulas',
        ref : 'filtroAulas'
    },
    {
        selector : 'selectorAulas',
        ref : 'selectorAulas'
    },
    {
        selector : 'panelCalendarioPorAula',
        ref : 'panelCalendarioPorAula'
    },
    {
        selector : 'panelCalendarioAulas button[name=imprimir]',
        ref : 'botonImprimir'
    }],
    
    init : function()
    {
        this.control(
        {
            'filtroAulas combobox[name=centro]' :
             {
                select : this.onCentroSelected
             },
             'filtroAulas combobox[name=semestre]' :
             {
                 select : this.onSemestreSelected
             },
             'filtroAulas combobox[name=edificio]' :
             {
                 select : this.onEdificioSelected
             }
        });
    },

    onCentroSelected : function()
    {
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]');
        
        semestre.clearValue();
        this.getFiltroAulas().down('combobox[name=edificio]').clearValue();
        this.getFiltroAulas().down('combobox[name=tipoAula]').clearValue();
        this.getFiltroAulas().down('combobox[name=planta]').clearValue();
        this.getBotonImprimir().hide();
        
        this.getStoreEdificiosStore().removeAll();
        this.getStoreTiposAulaStore().removeAll();
        this.getStorePlantasEdificioStore().removeAll();
        
        this.getSelectorAulas().removeAll();
        this.getPanelCalendarioPorAula().limpiaCalendario();
        
        var store = semestre.getStore();
        
        if (store.count() == 0)
        {
            store.loadData([ [ '1', '1' ], [ '2', '2' ] ]);
        }
    },
    
    onSemestreSelected : function()
    {
        this.getFiltroAulas().down('combobox[name=edificio]').clearValue();
        this.getFiltroAulas().down('combobox[name=tipoAula]').clearValue();
        this.getFiltroAulas().down('combobox[name=planta]').clearValue();
        this.getBotonImprimir().hide();

        
        this.getStoreTiposAulaStore().removeAll();
        this.getStorePlantasEdificioStore().removeAll();
        
        this.getSelectorAulas().removeAll();
        this.getPanelCalendarioPorAula().limpiaCalendario();
        
        var centro = this.getFiltroAulas().down('combobox[name=centro]').getValue();
        
        var store = this.getStoreEdificiosStore();
        
        store.load(
        {
            params :
            {
                centroId : centro
            },
            scope : this
        });
        
        fixLoadMaskBug(store, this.getFiltroAulas().down('combobox[name=edificio]'));
    },
    
    onEdificioSelected : function()
    {
        this.getFiltroAulas().down('combobox[name=tipoAula]').clearValue();
        this.getFiltroAulas().down('combobox[name=planta]').clearValue();
        
        var centro = this.getFiltroAulas().down('combobox[name=centro]').getValue();
        var edificio = this.getFiltroAulas().down('combobox[name=edificio]').getValue();
        this.getBotonImprimir().hide();

        
        this.getPanelCalendarioPorAula().limpiaCalendario();
        
        var storeTipos = this.getStoreTiposAulaStore();
        var storePlantas = this.getStorePlantasEdificioStore();
        
        var tiposAulas = this.getFiltroAulas().down('combobox[name=tipoAula]');
        
        storeTipos.load(
        {
           params : 
           {
               centroId : centro,
               edificio : edificio
           },
           callback: function(records, operation, success)
           {
               this.insert( 0, { nombre : 'Totes', valor : '' });
               tiposAulas.setValue('');
           }
        });
        
        fixLoadMaskBug(storeTipos, tiposAulas);
        
        var plantas = this.getFiltroAulas().down('combobox[name=planta]');
        
        storePlantas.load(
        {
            params : 
            {
                centroId : centro,
                edificio : edificio
            },
            callback: function(records, operation, success)
            {
                this.insert( 0, { nombre : 'Totes', valor : '' });
                plantas.setValue('');
            }           
        });
                
        fixLoadMaskBug(storePlantas, plantas);
    }
    
});