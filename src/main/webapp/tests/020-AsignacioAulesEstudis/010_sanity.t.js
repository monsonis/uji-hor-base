// also supports: startTest(function(t) {
StartTest(function(t) {
    t.diag("Sanity");  

    t.requireOk('HOR.model.Centro');
    t.requireOk('HOR.model.Estudio');
    t.requireOk('HOR.model.Semestre');    
    t.requireOk('HOR.controller.ControllerFiltroAsignacionAulas');
    t.requireOk('HOR.store.StoreCentros');
    t.requireOk('HOR.store.StoreEstudios');
    t.requireOk('HOR.store.StoreSemestres');
    t.requireOk('HOR.view.aulas.FiltroAsignacionAulas');    
    t.requireOk('HOR.view.aulas.PanelAulas');   

});


