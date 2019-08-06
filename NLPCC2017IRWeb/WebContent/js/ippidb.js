//Lance au chargement

/*$liste_test=array(
		"Surface Plasmon Resonance" =>"SPR",
		"ELISA" =>"ELISA",
		"FRET" =>"FRET",
		"Fluorescence Polarization" =>"FP",
		"HIV-induced cell death" =>"HIV-induced cell death",
		"cell-based screening assay" =>"cell-based screening assay",
		"gp160 fusion assay" =>"gp160 fusion assay",
		"gp120 fusion assay" =>"gp120 fusion assay",
		"jy-8 cell adhesion assay" =>"jy-8 cell adhesion assay",
		"Tcf4-luciferase reporter assay" =>"Tcf4-luciferase reporter assay",
		"hsb-2 cell adhesion assay" =>"hsb-2 cell adhesion assay",
		"hut-78 cell adhesion assay" =>"hut-78 cell adhesion assay",
		"Nuclear Magnetic Resonance" =>"NMR",
		"Isothermal Titration Calorimetry" =>"ITC",
		"Time-Resolved FRET" =>"TR-FRET",
		"alphascreen" =>"alphascreen",
		"Proliferation assay" =>"proliferation assay",
		"cellular viability" =>"cellular viability",
		"LPS-stimulated IL6 production" =>"LPS-stimulated IL6 production",
		"MLL-fusion leukaemic proliferation" =>"MLL-fusion leukaemic proliferation",
		"MLL-ENL-fusion leukaemic proliferation" =>"MLL-ENL-fusion leukaemic proliferation",
		"MLL-AF9-fusion leukaemic proliferation" =>"MLL-AF9-fusion leukaemic proliferation",
		"MTT assay" =>"MTT assay",
		"WST cell proliferation assay" =>"WST cell proliferation assay"
		);


var typeActivite = new Array();
typeActivite["IL2 / IL2R"]= new Array();
typeActivite["IL2 / IL2R"]["ELISA"]=new Array("pIC50");
typeActivite["IL2 / IL2R"]["Surface Plasmon Resonance"]=new Array("pIC50");

typeActivite["Myc / Max"]= new Array();
typeActivite["Myc / Max"]["Fluorescence Polarization"]=new Array("pIC50");
typeActivite["Myc / Max"]["FRET"]=new Array("pIC50");

typeActivite["CD4 / gp120"]= new Array();
typeActivite["CD4 / gp120"]["cell-based screening assay"]=new Array("pEC50", "pKd");
typeActivite["CD4 / gp120"]["gp120 fusion assay"]=new Array("pEC50");
typeActivite["CD4 / gp120"]["gp160 fusion assay"]=new Array("pEC50", "pIC50");
typeActivite["CD4 / gp120"]["HIV-induced cell death"]=new Array("pKd");

typeActivite["Beta-catenin / TCF-4"]= new Array();
typeActivite["Beta-catenin / TCF-4"]["Tcf4-luciferase reporter assay"]=new Array("pIC50");

typeActivite["LFA / ICAM"]= new Array();
typeActivite["LFA / ICAM"]["ELISA"]=new Array("pIC50");
typeActivite["LFA / ICAM"]["hsb-2 cell adhesion assay"]=new Array("pIC50");
typeActivite["LFA / ICAM"]["hut-78 cell adhesion assay"]=new Array("pIC50");
typeActivite["LFA / ICAM"]["jy-8 cell adhesion assay"]=new Array("pIC50");

typeActivite["CD80 / CD28"]= new Array();
typeActivite["CD80 / CD28"]["ELISA"]=new Array("pIC50");

typeActivite["ZipA / ftsZ"]= new Array();
typeActivite["ZipA / ftsZ"]["Fluorescence Polarization"]=new Array("pKi");

typeActivite["XIAP / Smac"]= new Array();
typeActivite["XIAP / Smac"]["alphascreen"]=new Array("pIC50");
typeActivite["XIAP / Smac"]["cellular viability"]=new Array("pIC50");
typeActivite["XIAP / Smac"]["ELISA"]=new Array("pIC50");
typeActivite["XIAP / Smac"]["Fluorescence Polarization"]=new Array("pIC50", "pKd", "pKi");
typeActivite["XIAP / Smac"]["Isothermal Titration Calorimetry"]=new Array("pKd");
typeActivite["XIAP / Smac"]["Nuclear Magnetic Resonance"]=new Array("pKd");
typeActivite["XIAP / Smac"]["Proliferation assay"]=new Array("pIC50");
typeActivite["XIAP / Smac"]["Time-Resolved FRET"]=new Array("pIC50");
typeActivite["XIAP / Smac"]["WST cell proliferation assay"]=new Array("pIC50");

typeActivite["MDM2-Like / P53"]= new Array();
typeActivite["MDM2-Like / P53"]["ELISA"]=new Array("pIC50");
typeActivite["MDM2-Like / P53"]["Fluorescence Polarization"]=new Array("pIC50", "pKd", "pKi");
typeActivite["MDM2-Like / P53"]["MTT assay"]=new Array("pIC50");
typeActivite["MDM2-Like / P53"]["Proliferation assay"]=new Array("pIC50");
typeActivite["MDM2-Like / P53"]["Surface Plasmon Resonance"]=new Array("pIC50");
typeActivite["MDM2-Like / P53"]["Time-Resolved FRET"]=new Array("pIC50");

typeActivite["BCL2-Like / BAX"]= new Array();
typeActivite["BCL2-Like / BAX"]["alphascreen"]=new Array("pIC50");
typeActivite["BCL2-Like / BAX"]["ELISA"]=new Array("pIC50");
typeActivite["BCL2-Like / BAX"]["Fluorescence Polarization"]=new Array("pIC50", "pKi");
typeActivite["BCL2-Like / BAX"]["Surface Plasmon Resonance"]=new Array("pIC50");
typeActivite["BCL2-Like / BAX"]["Time-Resolved FRET"]=new Array("pIC50", "pKi");

typeActivite["LEDGF / IN"]= new Array();
typeActivite["LEDGF / IN"]["alphascreen"]=new Array("pIC50");

typeActivite["E2 / E1"]= new Array();
typeActivite["E2 / E1"]["ELISA"]=new Array("pIC50");

typeActivite["Bromodomain / Histone"]= new Array();
typeActivite["Bromodomain / Histone"]["alphascreen"]=new Array("pIC50");
typeActivite["Bromodomain / Histone"]["cellular viability"]=new Array("pIC50");
typeActivite["Bromodomain / Histone"]["Fluorescence Polarization"]=new Array("pIC50");
typeActivite["Bromodomain / Histone"]["FRET"]=new Array("pIC50");
typeActivite["Bromodomain / Histone"]["Isothermal Titration Calorimetry"]=new Array("pKd");
typeActivite["Bromodomain / Histone"]["LPS-stimulated IL6 production"]=new Array("pIC50");
typeActivite["Bromodomain / Histone"]["MLL-AF9-fusion leukaemic proliferation"]=new Array("pIC50");
typeActivite["Bromodomain / Histone"]["MLL-ENL-fusion leukaemic proliferation"]=new Array("pIC50");
typeActivite["Bromodomain / Histone"]["MLL-fusion leukaemic proliferation"]=new Array("pIC50");
typeActivite["Bromodomain / Histone"]["Surface Plasmon Resonance"]=new Array("pKd");
typeActivite["Bromodomain / Histone"]["Time-Resolved FRET"]=new Array("pIC50");

var cibleTest = { 
    "IL2 / IL2R" : new Array(
            "ELISA",
            "Surface Plasmon Resonance"),
    "Myc / Max" : new Array(
            "Fluorescence Polarization",
            "FRET"),
    "CD4 / gp120": new Array(
            "cell-based screening assay",
            "gp120 fusion assay",
            "gp160 fusion assay",
            "HIV-induced cell death"
            ),
    "Beta-catenin / TCF-4": new Array(
            "Tcf4-luciferase reporter assay"),
    "LFA / ICAM": new Array(            
            "ELISA",
            "hsb-2 cell adhesion assay",
            "hut-78 cell adhesion assay",
            "jy-8 cell adhesion assay"
            ),
    "CD80 / CD28": new Array(
            "ELISA"),
    "ZipA / ftsZ": new Array(
            "Fluorescence Polarization"),
    "XIAP / Smac": new Array(
            "alphascreen",
            "cellular viability",
            "ELISA",
            "Fluorescence Polarization",
            "Isothermal Titration Calorimetry",
            "Nuclear Magnetic Resonance",
            "Proliferation assay",           
            "Time-Resolved FRET",            
            "WST cell proliferation assay"
            ),
    "MDM2-Like / P53": new Array(
            "ELISA",
            "Fluorescence Polarization",
            "MTT assay",
            "Proliferation assay",
            "Surface Plasmon Resonance",
            "Time-Resolved FRET"
            ), 
    "BCL2-Like / BAX": new Array(
            "alphascreen",
            "ELISA",
            "Fluorescence Polarization",            
            "Surface Plasmon Resonance",
            "Time-Resolved FRET"
            ),
    "LEDGF / IN": new Array(
            "alphascreen"
            ),
    "E2 / E1": new Array(
            "ELISA"
            ),
    "Bromodomain / Histone": new Array(
            "alphascreen",
            "cellular viability",
            "Fluorescence Polarization",
            "FRET",
            "Isothermal Titration Calorimetry",
            "LPS-stimulated IL6 production",
            "MLL-AF9-fusion leukaemic proliferation",
            "MLL-ENL-fusion leukaemic proliferation",
            "MLL-fusion leukaemic proliferation",
            "Surface Plasmon Resonance",
            "Time-Resolved FRET"                       
            )
    };
    
    var cibleActivite = { 
    "IL2 / IL2R" : new Array(
            "pIC50"),
    "Myc / Max" : new Array(
            "pIC50"),
    "CD4 / gp120": new Array(
            "pEC50",
            "pIC50",
            "pKd"),
    "Beta-catenin / TCF-4": new Array(
            "pIC50"),
    "LFA / ICAM": new Array(
            "pIC50"),
    "CD80 / CD28": new Array(
            "pIC50"),
    "ZipA / ftsZ": new Array(
            "pKi"),
    "XIAP / Smac": new Array(
            "pIC50",
            "pKd",
            "pKi"),
    "MDM2-Like / P53": new Array(
            "pIC50",
            "pKd",
            "pKi"),
    "BCL2-Like / BAX": new Array(
            "pIC50",
            "pKi"),
    "LEDGF / IN":  new Array(
            "pIC50"),
    "E2 / E1":  new Array(
            "pIC50"),
    "Bromodomain / Histone": new Array(
            "pIC50",
            "pKd")
    };
    
    var cibleTestUser = { 
    "IL2 / IL2R" : new Array(
            "ELISA",
            "Surface Plasmon Resonance"),
    "Myc / Max" : new Array(
            "Fluorescence Polarization",
            "FRET"),
    "CD4 / gp120": new Array(
            "cell-based screening assay",
            "gp120 fusion assay",
            "gp160 fusion assay",
            "HIV-induced cell death"
            ),
    "Beta-catenin / TCF-4": new Array(
            "Tcf4-luciferase reporter assay"),
    "LFA / ICAM": new Array(            
            "ELISA",
            "hsb-2 cell adhesion assay",
            "hut-78 cell adhesion assay",
            "jy-8 cell adhesion assay"
            ),
    "CD80 / CD28": new Array(
            "ELISA"),
    "ZipA / ftsZ": new Array(
            "Fluorescence Polarization"),
    "XIAP / Smac": new Array(
            "alphascreen",
            "cellular viability",
            "ELISA",
            "Fluorescence Polarization",
            "Isothermal Titration Calorimetry",
            "Nuclear Magnetic Resonance",
            "Proliferation assay",           
            "Time-Resolved FRET"
            ),
    "MDM2-Like / P53": new Array(
            "ELISA",
            "Fluorescence Polarization",
            "Proliferation assay",
            "Surface Plasmon Resonance",
            "Time-Resolved FRET"
            ), 
    "BCL2-Like / BAX": new Array(
            "alphascreen",
            "ELISA",
            "Fluorescence Polarization",            
            "Surface Plasmon Resonance",
            "Time-Resolved FRET"
            ),
    "LEDGF / IN": new Array(
            "alphascreen"
            ),
    "E2 / E1": new Array(
            "ELISA"
            ),
    "Bromodomain / Histone": new Array(
            "alphascreen",
            "cellular viability",
            "Fluorescence Polarization",
            "FRET",
            "Isothermal Titration Calorimetry",
            "LPS-stimulated IL6 production",
            "MLL-AF9-fusion leukaemic proliferation",
            "MLL-ENL-fusion leukaemic proliferation",
            "MLL-fusion leukaemic proliferation",
            "Surface Plasmon Resonance",
            "Time-Resolved FRET"                       
            )
    };
    
    var cibleActiviteUser = { 
    "IL2 / IL2R" : new Array(
            "pIC50"),
    "Myc / Max" : new Array(
            "pIC50"),
    "CD4 / gp120": new Array(
            "pEC50",
            "pKd"),
    "Beta-catenin / TCF-4": new Array(
            "pIC50"),
    "LFA / ICAM": new Array(
            "pIC50"),
    "CD80 / CD28": new Array(
            "pIC50"),
    "ZipA / ftsZ": new Array(
            "pKi"),
    "XIAP / Smac": new Array(
            "pIC50",
            "pKd",
            "pKi"),
    "MDM2-Like / P53": new Array(
            "pIC50",
            "pKi"),
    "BCL2-Like / BAX": new Array(
            "pIC50",
            "pKi"),
    "LEDGF / IN":  new Array(
            "pIC50"),
    "E2 / E1":  new Array(
            "pIC50"),
    "Bromodomain / Histone": new Array(
            "pIC50",
            "pKd")
    };


var typeActiviteUser = new Array();
typeActiviteUser["IL2 / IL2R"]= new Array();
typeActiviteUser["IL2 / IL2R"]["ELISA"]=new Array("pIC50");
typeActiviteUser["IL2 / IL2R"]["Surface Plasmon Resonance"]=new Array("pIC50");

typeActiviteUser["Myc / Max"]= new Array();
typeActiviteUser["Myc / Max"]["Fluorescence Polarization"]=new Array("pIC50");
typeActiviteUser["Myc / Max"]["FRET"]=new Array("pIC50");

typeActiviteUser["CD4 / gp120"]= new Array();
typeActiviteUser["CD4 / gp120"]["cell-based screening assay"]=new Array("pEC50", "pKd");
typeActiviteUser["CD4 / gp120"]["gp120 fusion assay"]=new Array("pEC50");
typeActiviteUser["CD4 / gp120"]["gp160 fusion assay"]=new Array("pEC50");
typeActiviteUser["CD4 / gp120"]["HIV-induced cell death"]=new Array("pKd");

typeActiviteUser["Beta-catenin / TCF-4"]= new Array();
typeActiviteUser["Beta-catenin / TCF-4"]["Tcf4-luciferase reporter assay"]=new Array("pIC50");

typeActiviteUser["LFA / ICAM"]= new Array();
typeActiviteUser["LFA / ICAM"]["ELISA"]=new Array("pIC50");
typeActiviteUser["LFA / ICAM"]["hsb-2 cell adhesion assay"]=new Array("pIC50");
typeActiviteUser["LFA / ICAM"]["jy-8 cell adhesion assay"]=new Array("pIC50");
typeActiviteUser["LFA / ICAM"]["hut-78 cell adhesion assay"]=new Array("pIC50");

typeActiviteUser["CD80 / CD28"]= new Array();
typeActiviteUser["CD80 / CD28"]["ELISA"]=new Array("pIC50");

typeActiviteUser["ZipA / ftsZ"]= new Array();
typeActiviteUser["ZipA / ftsZ"]["Fluorescence Polarization"]=new Array("pKi");

typeActiviteUser["XIAP / Smac"]= new Array();
typeActiviteUser["XIAP / Smac"]["alphascreen"]=new Array("pIC50");
typeActiviteUser["XIAP / Smac"]["cellular viability"]=new Array("pIC50");
typeActiviteUser["XIAP / Smac"]["ELISA"]=new Array("pIC50");
typeActiviteUser["XIAP / Smac"]["Fluorescence Polarization"]=new Array("pIC50", "pKd", "pKi");
typeActiviteUser["XIAP / Smac"]["Isothermal Titration Calorimetry"]=new Array("pKd");
typeActiviteUser["XIAP / Smac"]["Nuclear Magnetic Resonance"]=new Array("pKd");
typeActiviteUser["XIAP / Smac"]["Proliferation assay"]=new Array("pIC50");
typeActiviteUser["XIAP / Smac"]["Time-Resolved FRET"]=new Array("pIC50");

typeActiviteUser["MDM2-Like / P53"]= new Array();
typeActiviteUser["MDM2-Like / P53"]["ELISA"]=new Array("pIC50");
typeActiviteUser["MDM2-Like / P53"]["Fluorescence Polarization"]=new Array("pIC50", "pKi");
typeActiviteUser["MDM2-Like / P53"]["Proliferation assay"]=new Array("pIC50");
typeActiviteUser["MDM2-Like / P53"]["Surface Plasmon Resonance"]=new Array("pIC50");
typeActiviteUser["MDM2-Like / P53"]["Time-Resolved FRET"]=new Array("pIC50");

typeActiviteUser["BCL2-Like / BAX"]= new Array();
typeActiviteUser["BCL2-Like / BAX"]["alphascreen"]=new Array("pIC50");
typeActiviteUser["BCL2-Like / BAX"]["ELISA"]=new Array("pIC50");
typeActiviteUser["BCL2-Like / BAX"]["Fluorescence Polarization"]=new Array("pIC50", "pKi");
typeActiviteUser["BCL2-Like / BAX"]["Surface Plasmon Resonance"]=new Array("pIC50");
typeActiviteUser["BCL2-Like / BAX"]["Time-Resolved FRET"]=new Array("pIC50", "pKi");

typeActiviteUser["LEDGF / IN"]= new Array();
typeActiviteUser["LEDGF / IN"]["alphascreen"]=new Array("pIC50");

typeActiviteUser["E2 / E1"]= new Array();
typeActiviteUser["E2 / E1"]["ELISA"]=new Array("pIC50");

typeActiviteUser["Bromodomain / Histone"]= new Array();
typeActiviteUser["Bromodomain / Histone"]["alphascreen"]=new Array("pIC50");
typeActiviteUser["Bromodomain / Histone"]["cellular viability"]=new Array("pIC50");
typeActiviteUser["Bromodomain / Histone"]["Fluorescence Polarization"]=new Array("pIC50");
typeActiviteUser["Bromodomain / Histone"]["FRET"]=new Array("pIC50");
typeActiviteUser["Bromodomain / Histone"]["Isothermal Titration Calorimetry"]=new Array("pKd");
typeActiviteUser["Bromodomain / Histone"]["LPS-stimulated IL6 production"]=new Array("pIC50");
typeActiviteUser["Bromodomain / Histone"]["MLL-AF9-fusion leukaemic proliferation"]=new Array("pIC50");
typeActiviteUser["Bromodomain / Histone"]["MLL-ENL-fusion leukaemic proliferation"]=new Array("pIC50");
typeActiviteUser["Bromodomain / Histone"]["MLL-fusion leukaemic proliferation"]=new Array("pIC50");
typeActiviteUser["Bromodomain / Histone"]["Surface Plasmon Resonance"]=new Array("pKd");
typeActiviteUser["Bromodomain / Histone"]["Time-Resolved FRET"]=new Array("pIC50");

var cibleDrug = new Array(); 
    cibleDrug["IL2 / IL2R"] = "Y";
    cibleDrug["Myc / Max"] = "N";
    cibleDrug["CD4 / gp120"]= "Y";
    cibleDrug["Beta-catenin / TCF-4"]= "Y";
    cibleDrug["LFA / ICAM"]= "Y";
    cibleDrug["CD80 / CD28"]= "N";
    cibleDrug["ZipA / ftsZ"]= "N";
    cibleDrug["XIAP / Smac"]= "Y";
    cibleDrug["MDM2-Like / P53"]= "Y";
    cibleDrug["BCL2-Like / BAX"]= "Y";
    cibleDrug["LEDGF / IN"]=  "Y";
    cibleDrug["E2 / E1"]=  "N";
    cibleDrug["Bromodomain / Histone"]= "Y";
    
*/


/*
function adapteMenuTest(mode)
{
    //alert("toto");
    

    // Cas ou l utilisateur remet le choix par defaut, on reinitialise les menus et bloc l acces
    
    if(document.forms.formSelect.family.value == 'default')
    {
        document.forms.formSelect.testName.innerHTML = "";
        document.forms.formSelect.testName.disabled = true;
        
        document.forms.formSelect.pic50.innerHTML = "";
        document.forms.formSelect.pic50.disabled = true;
        
        // on desactive le choix de selection sur drug
        document.forms.formSelect.selectDrugOnly.checked=false;
        document.forms.formSelect.selectDrugOnly.disabled = true;
        
        document.forms.formSelect.pfizer.disabled = false;
            document.forms.formSelect.lipinski.disabled = false;
            document.forms.formSelect.veber.disabled = false;
        
            // desactive les descripteurs
            document.forms.formSelect.nROT.disabled = false;
            document.forms.formSelect.TPSA.disabled = false;
            document.forms.formSelect.NbHBondDon.disabled = false;
            document.forms.formSelect.NbHBondAcc.disabled = false;
            document.forms.formSelect.AlogP.disabled = false;        
            document.forms.formSelect.MW.disabled = false;
            document.forms.formSelect.AlogP.disabled = false;
            document.forms.formSelect.nArSR.disabled = false;
            document.forms.formSelect.FSP3.disabled = false;
            document.forms.formSelect.nChiralCenters.disabled = false;
        
            // desactive les tests, type de tests et fourchette activite
            document.forms.formSelect.pic50.disabled = false;
            document.forms.formSelect.testName.disabled = false;
            document.forms.formSelect.activityMin.disabled = false;
            document.forms.formSelect.activityMax.disabled = false;
    }
    else
    {
        // on traite le menu des test
        // on met une premiere option pour garder tous les tests, puis ceux disponible pour la famille selectionnee
        document.forms.formSelect.testName.innerHTML = "";
        var optn = document.createElement("OPTION");
        optn.value = 'default';
        optn.text = 'all tests';
        document.forms.formSelect.testName.options.add(optn);
        
        // les tests dependent du mode de l utilisateur
        if (mode == 'contributor')
        {
            for(var i= 0; i < cibleTest[document.forms.formSelect.family.value].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = cibleTest[document.forms.formSelect.family.value][i];
                optn.text = cibleTest[document.forms.formSelect.family.value][i];
                document.forms.formSelect.testName.options.add(optn);
        
            }
        }
        else
        {
            for(var i= 0; i < cibleTestUser[document.forms.formSelect.family.value].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = cibleTestUser[document.forms.formSelect.family.value][i];
                optn.text = cibleTestUser[document.forms.formSelect.family.value][i];
                document.forms.formSelect.testName.options.add(optn);
        
            }
        }
        // on rend le menu visible
        document.forms.formSelect.testName.disabled = false;
    
        // on traite le menu des activites. Si le menu des tests est change alors le menu des activites sera reactualise en consequences
        document.forms.formSelect.pic50.innerHTML = "";
    
        var optn = document.createElement("OPTION");
        optn.value = 'default';
        optn.text = 'all activity types';
        document.forms.formSelect.pic50.options.add(optn);
     
        if (mode == 'contributor')
        {
            for(var i= 0; i < cibleActivite[document.forms.formSelect.family.value].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = cibleActivite[document.forms.formSelect.family.value][i];
                optn.text = cibleActivite[document.forms.formSelect.family.value][i];
                document.forms.formSelect.pic50.options.add(optn);
                
            } 
         }
        else
        {
            for(var i= 0; i < cibleActiviteUser[document.forms.formSelect.family.value].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = cibleActiviteUser[document.forms.formSelect.family.value][i];
                optn.text = cibleActiviteUser[document.forms.formSelect.family.value][i];
                document.forms.formSelect.pic50.options.add(optn);
                
            } 
        }       
        document.forms.formSelect.pic50.disabled = false;
    
        // On regarde si il y a des drug dans iPPIDB pour cette cible et si oui on active le choix
    
        if (cibleDrug[document.forms.formSelect.family.value] == "Y")
        {
            document.forms.formSelect.selectDrugOnly.disabled = false;
            
            if (document.forms.formSelect.selectDrugOnly.checked == true)
            {
                document.forms.formSelect.selectDrugOnly.checked = false;
                document.forms.formSelect.pfizer.disabled = false;
                document.forms.formSelect.lipinski.disabled = false;
                document.forms.formSelect.veber.disabled = false;
        
                // desactive les descripteurs
                document.forms.formSelect.nROT.disabled = false;
                document.forms.formSelect.TPSA.disabled = false;
                document.forms.formSelect.NbHBondDon.disabled = false;
                document.forms.formSelect.NbHBondAcc.disabled = false;
                document.forms.formSelect.AlogP.disabled = false;        
                document.forms.formSelect.MW.disabled = false;
                document.forms.formSelect.AlogP.disabled = false;
                document.forms.formSelect.nArSR.disabled = false;
                document.forms.formSelect.FSP3.disabled = false;
                document.forms.formSelect.nChiralCenters.disabled = false;
        
                // desactive les tests, type de tests et fourchette activite
                document.forms.formSelect.pic50.disabled = false;
                document.forms.formSelect.testName.disabled = false;
                document.forms.formSelect.activityMin.disabled = false;
                document.forms.formSelect.activityMax.disabled = false;
            }
        }
        else
        {
            document.forms.formSelect.selectDrugOnly.checked=false;
            document.forms.formSelect.selectDrugOnly.disabled = true;
            
            document.forms.formSelect.pfizer.disabled = false;
            document.forms.formSelect.lipinski.disabled = false;
            document.forms.formSelect.veber.disabled = false;
        
            // desactive les descripteurs
            document.forms.formSelect.nROT.disabled = false;
            document.forms.formSelect.TPSA.disabled = false;
            document.forms.formSelect.NbHBondDon.disabled = false;
            document.forms.formSelect.NbHBondAcc.disabled = false;
            document.forms.formSelect.AlogP.disabled = false;        
            document.forms.formSelect.MW.disabled = false;
            document.forms.formSelect.AlogP.disabled = false;
            document.forms.formSelect.nArSR.disabled = false;
            document.forms.formSelect.FSP3.disabled = false;
            document.forms.formSelect.nChiralCenters.disabled = false;
        
            // desactive les tests, type de tests et fourchette activite
            document.forms.formSelect.pic50.disabled = false;
            document.forms.formSelect.testName.disabled = false;
            document.forms.formSelect.activityMin.disabled = false;
            document.forms.formSelect.activityMax.disabled = false;
        }

    }
    
        
} */

function adapteMenuActiviteDynamique()
{

 // Cas ou l utilisateur remet le choix par defaut, on reinitialise les menus et bloc l acces
    
    if(document.forms.formSelect.testName.value == 'default')
    {
        document.forms.formSelect.pic50.innerHTML = "";
        
        var optn = document.createElement("OPTION");
        optn.value = 'default';
        optn.text = 'all activity types';
        document.forms.formSelect.pic50.options.add(optn);
        
        xmlHttp = GetXmlHttpObject();
        if (xmlHttp == null)
        {
            alert("Votre navigateur ne supporte pas les requêtes HTTP.");
            return false;
        } 
        var url="recupListeTests.php";
        
        xmlHttp.onreadystatechange = function (){
            if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
            {
                eval(xmlHttp.responseText);
        
                for(var i= 0; i < listeActivityType.length; i++)
                {
                    var optn = document.createElement("OPTION");
                    optn.value = listeActivityType[i];
                    optn.text = listeActivityType[i];
                    document.forms.formSelect.pic50.options.add(optn);
                
                }
            }
        }
       // alert('?');
        xmlHttp.open("POST", url, true);
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
        varSend="famille="+document.forms.formSelect.family.value;
        //alert("test");
        xmlHttp.send(varSend);
        return true;    

    }    
    else
    {
        // on traite le menu des test
        // on met une premiere option pour garder tous les tests, puis ceux disponible pour la famille selectionnee
        document.forms.formSelect.pic50.innerHTML = "";
        var optn = document.createElement("OPTION");
        optn.value = 'default';
        optn.text = 'all activity types';
        document.forms.formSelect.pic50.options.add(optn);
                
        // on récupère la liste des tests pour cette famille et on les met dans le menu
        xmlHttp = GetXmlHttpObject();
        if (xmlHttp == null)
        {
            alert("Votre navigateur ne supporte pas les requêtes HTTP.");
            return false;
        } 
        var url="recupListeActivites.php";
        
        xmlHttp.onreadystatechange = function (){
            if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
            {
                eval(xmlHttp.responseText);
                //alert(listeActivites.length);
                for(var i= 0; i < listeActivites.length; i++)
                {
                    var optn = document.createElement("OPTION");
                    optn.value = listeActivites[i];
                    optn.text = listeActivites[i];
                    document.forms.formSelect.pic50.options.add(optn);
                    // on rend le menu visible
                    document.forms.formSelect.pic50.disabled = false;
        
                }
            }
         }
       // alert('?');
        xmlHttp.open("POST", url, true);
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
        varSend="famille="+document.forms.formSelect.family.value+"&test="+document.forms.formSelect.testName.value;
        //alert("test");
        xmlHttp.send(varSend);
        return true;    

    }  
}

function adapteMenuTestDynamique()
{

    // Cas ou l utilisateur remet le choix par defaut, on reinitialise les menus et bloc l acces
    
    if(document.forms.formSelect.family.value == 'default')
    {
        document.forms.formSelect.testName.innerHTML = "";
        document.forms.formSelect.testName.disabled = true;
        
        document.forms.formSelect.pic50.innerHTML = "";
        document.forms.formSelect.pic50.disabled = true;
        
        // on desactive le choix de selection sur drug
        document.forms.formSelect.selectDrugOnly.checked=false;
        document.forms.formSelect.selectDrugOnly.disabled = true;
        
        document.forms.formSelect.pfizer.disabled = false;
            document.forms.formSelect.lipinski.disabled = false;
            document.forms.formSelect.veber.disabled = false;
            // au cas où la famille précédente n'avait pas de drug, on remet par défaut
            
            document.getElementById("noDrugMsg").innerHTML= "&nbsp;";
            document.getElementById("noDrugMsg").style.visibility="hidden";
        
            // desactive les descripteurs
            document.forms.formSelect.nROT.disabled = false;
            document.forms.formSelect.TPSA.disabled = false;
            document.forms.formSelect.NbHBondDon.disabled = false;
            document.forms.formSelect.NbHBondAcc.disabled = false;
            document.forms.formSelect.AlogP.disabled = false;        
            document.forms.formSelect.MW.disabled = false;
            document.forms.formSelect.AlogP.disabled = false;
            document.forms.formSelect.nArSR.disabled = false;
            document.forms.formSelect.FSP3.disabled = false;
            document.forms.formSelect.nChiralCenters.disabled = false;
        
            // desactive les tests, type de tests et fourchette activite
            document.forms.formSelect.pic50.disabled = false;
            document.forms.formSelect.testName.disabled = false;
            document.forms.formSelect.activityMin.disabled = false;
            document.forms.formSelect.activityMax.disabled = false;
    }
    else
    {
        // on traite le menu des test
        // on met une premiere option pour garder tous les tests, puis ceux disponible pour la famille selectionnee
        document.forms.formSelect.testName.innerHTML = "";
        var optn = document.createElement("OPTION");
        optn.value = 'default';
        optn.text = 'all tests';
        document.forms.formSelect.testName.options.add(optn);
        //alert("jjjhhh");
        
        // on récupère la liste des tests pour cette famille et on les met dans le menu
        xmlHttp = GetXmlHttpObject();
        if (xmlHttp == null)
        {
            alert("Votre navigateur ne supporte pas les requêtes HTTP.");
            return false;
        } 
        var url="recupListeTests.php";
        
        xmlHttp.onreadystatechange = function (){
            if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
            {
                eval(xmlHttp.responseText);
                //alert("ici");
                //alert(listeTests.length);
                for(var i= 0; i < listeTests.length; i++)
                {
                    var optn = document.createElement("OPTION");
                    optn.value = listeTests[i];
                    optn.text = listeTests[i];
                    document.forms.formSelect.testName.options.add(optn);
                    // on rend le menu visible
                    document.forms.formSelect.testName.disabled = false;
        
                }
                
                document.forms.formSelect.pic50.innerHTML = "";
    
                var optn = document.createElement("OPTION");
                optn.value = 'default';
                optn.text = 'all activity types';
                document.forms.formSelect.pic50.options.add(optn);
                
                for(var i= 0; i < listeActivityType.length; i++)
                {
                    var optn = document.createElement("OPTION");
                    optn.value = listeActivityType[i];
                    optn.text = listeActivityType[i];
                    document.forms.formSelect.pic50.options.add(optn);
                
                } 
                
                document.forms.formSelect.pic50.disabled = false;

                if (nbDrug > 0)
                {
                    document.forms.formSelect.selectDrugOnly.disabled = false;
                    document.getElementById("noDrugMsg").innerHTML= "&nbsp;";
                    document.getElementById("noDrugMsg").style.visibility="hidden";
            
                }
                else
                {
                    document.forms.formSelect.selectDrugOnly.disabled = true;
                    document.getElementById("noDrugMsg").innerHTML= "<img src='img/attentionSimilarite_h25.png' class='imgTexte'/>&nbsp;&nbsp;<p id='warningDrug'>This PPI target doesn't have any drug candidates yet.</p>";
                    document.getElementById("noDrugMsg").style.visibility="visible";
            
                }
            

            }
         }
       // alert('?');
        xmlHttp.open("POST", url, true);
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
        varSend="famille="+document.forms.formSelect.family.value;
        //alert("test");
        xmlHttp.send(varSend);
        return true;
    

      /*  for(var i= 0; i < cibleTest[document.forms.formSelect.family.value].length; i++)
        {
                var optn = document.createElement("OPTION");
                optn.value = cibleTest[document.forms.formSelect.family.value][i];
                optn.text = cibleTest[document.forms.formSelect.family.value][i];
                document.forms.formSelect.testName.options.add(optn);
        
        }
       
        // on rend le menu visible
        document.forms.formSelect.testName.disabled = false;
    
        // on traite le menu des activites. Si le menu des tests est change alors le menu des activites sera reactualise en consequences
        document.forms.formSelect.pic50.innerHTML = "";
    
        var optn = document.createElement("OPTION");
        optn.value = 'default';
        optn.text = 'all activity types';
        document.forms.formSelect.pic50.options.add(optn);
     
        if (mode == 'contributor')
        {
            for(var i= 0; i < cibleActivite[document.forms.formSelect.family.value].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = cibleActivite[document.forms.formSelect.family.value][i];
                optn.text = cibleActivite[document.forms.formSelect.family.value][i];
                document.forms.formSelect.pic50.options.add(optn);
                
            } 
         }
        else
        {
            for(var i= 0; i < cibleActiviteUser[document.forms.formSelect.family.value].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = cibleActiviteUser[document.forms.formSelect.family.value][i];
                optn.text = cibleActiviteUser[document.forms.formSelect.family.value][i];
                document.forms.formSelect.pic50.options.add(optn);
                
            } 
        }       
        document.forms.formSelect.pic50.disabled = false;
    
        // On regarde si il y a des drug dans iPPIDB pour cette cible et si oui on active le choix
    if (cibleDrug[document.forms.formSelect.family.value] == "Y")
                {
                    document.forms.formSelect.selectDrugOnly.disabled = false;
       if (document.forms.formSelect.selectDrugOnly.checked == true)
                    {
                document.forms.formSelect.selectDrugOnly.checked = false;
                document.forms.formSelect.pfizer.disabled = false;
                document.forms.formSelect.lipinski.disabled = false;
                document.forms.formSelect.veber.disabled = false;
        
                // desactive les descripteurs
                document.forms.formSelect.nROT.disabled = false;
                document.forms.formSelect.TPSA.disabled = false;
                document.forms.formSelect.NbHBondDon.disabled = false;
                document.forms.formSelect.NbHBondAcc.disabled = false;
                document.forms.formSelect.AlogP.disabled = false;        
                document.forms.formSelect.MW.disabled = false;
                document.forms.formSelect.AlogP.disabled = false;
                document.forms.formSelect.nArSR.disabled = false;
                document.forms.formSelect.FSP3.disabled = false;
                document.forms.formSelect.nChiralCenters.disabled = false;
        
                // desactive les tests, type de tests et fourchette activite
                document.forms.formSelect.pic50.disabled = false;
                document.forms.formSelect.testName.disabled = false;
                document.forms.formSelect.activityMin.disabled = false;
                document.forms.formSelect.activityMax.disabled = false;
            }
        }
        else
        {
            document.forms.formSelect.selectDrugOnly.checked=false;
            document.forms.formSelect.selectDrugOnly.disabled = true;
            
            document.forms.formSelect.pfizer.disabled = false;
            document.forms.formSelect.lipinski.disabled = false;
            document.forms.formSelect.veber.disabled = false;
        
            // desactive les descripteurs
            document.forms.formSelect.nROT.disabled = false;
            document.forms.formSelect.TPSA.disabled = false;
            document.forms.formSelect.NbHBondDon.disabled = false;
            document.forms.formSelect.NbHBondAcc.disabled = false;
            document.forms.formSelect.AlogP.disabled = false;        
            document.forms.formSelect.MW.disabled = false;
            document.forms.formSelect.AlogP.disabled = false;
            document.forms.formSelect.nArSR.disabled = false;
            document.forms.formSelect.FSP3.disabled = false;
            document.forms.formSelect.nChiralCenters.disabled = false;
        
            // desactive les tests, type de tests et fourchette activite
            document.forms.formSelect.pic50.disabled = false;
            document.forms.formSelect.testName.disabled = false;
            document.forms.formSelect.activityMin.disabled = false;
            document.forms.formSelect.activityMax.disabled = false;
        }
        }
 */
//}
    }    
        
}

/*

function adapteMenuActivite(mode)
{

    // Cas ou l utilisateur remet le choix par defaut, on reinitialise les menus et bloc l acces
    
    if(document.forms.formSelect.testName.value == 'default')
    {
        document.forms.formSelect.pic50.innerHTML = "";
        
        var optn = document.createElement("OPTION");
        optn.value = 'default';
        optn.text = 'all activity types';
        document.forms.formSelect.pic50.options.add(optn);
     
        if (mode == 'contributor')
        {

            for(var i= 0; i < cibleActivite[document.forms.formSelect.family.value].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = cibleActivite[document.forms.formSelect.family.value][i];
                optn.text = cibleActivite[document.forms.formSelect.family.value][i];
                document.forms.formSelect.pic50.options.add(optn);
                
            }
        }
        else
        {
            for(var i= 0; i < cibleActiviteUser[document.forms.formSelect.family.value].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = cibleActiviteUser[document.forms.formSelect.family.value][i];
                optn.text = cibleActiviteUser[document.forms.formSelect.family.value][i];
                document.forms.formSelect.pic50.options.add(optn);
                
            } 
        }    
    }
    else
    {
        document.forms.formSelect.pic50.innerHTML = "";
        var optn = document.createElement("OPTION");
        optn.value = 'default';
        optn.text = 'all activity types';
        document.forms.formSelect.pic50.options.add(optn);
     
        var famille = document.forms.formSelect.family.value;
        var test = document.forms.formSelect.testName.value;
       
        if (mode == 'contributor')
        {
            for(var i= 0; i < typeActivite[famille][test].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = typeActivite[famille][test][i];
                optn.text = typeActivite[famille][test][i];
                document.forms.formSelect.pic50.options.add(optn);
                
            }
        }
        else
        {
            for(var i= 0; i < typeActiviteUser[famille][test].length; i++)
            {
                var optn = document.createElement("OPTION");
                optn.value = typeActiviteUser[famille][test][i];
                optn.text = typeActiviteUser[famille][test][i];
                document.forms.formSelect.pic50.options.add(optn);
                
            }
        }

        
        
        document.forms.formSelect.pic50.disabled = false;
    }
}
*/
// verification du contenu des champs pour le formulaire selectionCritere

function verif_champsSelectionCriteres(nChiralCenters, FSP3, nArSR, nROT, TPSA, NbHBondAcc, NbHBondDon, AlogP, MW, activityMin, activityMax, family)
{	
    var probleme_detecte = false;
	var message = '';
    	
    if (activityMin != "")
    {
        if (isNaN(activityMin))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for the minimal activity !';
    	}
    }
    
    if (activityMax != "")
    {
        if (isNaN(activityMax))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for the maximal activity !';
    	}
    }
      
    if (MW != "")
    {
        if (isNaN(MW))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for the Molecular Weight !';
    	}
    }
    
    if (AlogP != "")
    {
        if (isNaN(AlogP))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for AlogP !';
    	}
    }
    
    if (NbHBondDon != "")
    {
        if (isNaN(NbHBondDon))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for the number of H Bond Donors !';
    	}
    }
    
    if (NbHBondAcc != "")
    {
        if (isNaN(NbHBondAcc))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for the number of H Bond Acceptors !';
    	}
    }
    
    if (TPSA != "")
    {
        if (isNaN(TPSA))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for TPSA !';
    	}
    }
    
    if (nROT != "")
    {
        if (isNaN(nROT))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for the number of Rotatable Bonds !';
    	}
    }
    
    if (nArSR != "")
    {
        if (isNaN(nArSR))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for the number of Aromatic Rings !';
    	}
    }
    
    if (FSP3 != "")
    {
        if (isNaN(FSP3))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for Fsp3 !';
    	}
    }
    
    if (nChiralCenters != "")
    {
        if (isNaN(nChiralCenters))
    	{
       		 probleme_detecte = true;
       		 message = 'Invalid value for the number of chiral centers !';
    	}
    }
    
    if (family == "default")
    {
     	probleme_detecte = true;
       	message = 'Please, choose a PPI (mandatory field) !';
    }
  
    if (probleme_detecte)
	{
        alert(message);
        probleme_detecte = true;
        message = '';
        return false;
    }
    else
    {
        return true;
    }
}


//verification du contenu des champs pour le formulaire Inscription
function verif_champsInscription(nom, prenom, societe, ville, pays, mail, antispam)
{	
    var probleme_detecte = false;
	var message = '';
       
    if (antispam == "")
    {
        probleme_detecte = true;
        message = 'The antispam field is empty !';
    }
        
    if (mail == "")
    {
        probleme_detecte = true;
        message = 'The mail field is empty !';
    }
    else
    {
    
        indexAroba = mail.indexOf('@');
        indexPoint = mail.indexOf('.');
        
        if ((indexAroba < 0) || (indexPoint < 0))
        {
            probleme_detecte = true;
            message = 'The mail field is not valid !';
        }
        
    }
    
    if (pays == "")
    {
        probleme_detecte = true;
        message = 'The country field is empty !';
    }
    /*else
    {
        if(!(/[a-zA-Z]*//*.exec(pays)))
        {
        
            probleme_detecte = true;
            message = 'The country field is not valid !';
        }
    }*/
    
    if (ville == "")
    {
        probleme_detecte = true;
        message = 'The town / city field is empty !';
    }
    
    if (societe == "")
    {
        probleme_detecte = true;
        message = 'The organization\'s name field is empty !';
    }
    
    if (prenom == "")
    {
        probleme_detecte = true;
        message = 'The first name field is empty !';
    }
    
    if (nom == "")
    {
        probleme_detecte = true;
        message = 'The last name field is empty !';
    }
    
    if (probleme_detecte)
	{
        alert(message);
        probleme_detecte = true;
        message = '';
        return false;
    }
    else
    {
        return true;
    }
}



//verification du contenu des champs pour le formulaire PPI-HitProfiler
function verif_champsLogIn(login, mdp)
{	
    var probleme_detecte = false;
	var message = '';
        
    if (mdp == "")
    {
        probleme_detecte = true;
        message = 'The password field is empty !';
    }
    
    if (login == "")
    {
        probleme_detecte = true;
        message = 'The login field is empty !';
    }
    else
    {
        indexAroba = login.indexOf('@');
        indexPoint = login.indexOf('.');
        
        if ((indexAroba < 0) || (indexPoint < 0))
        {
            probleme_detecte = true;
            message = 'The login field is not valid !';
        }
        
    }
    
    if (probleme_detecte)
	{
        alert(message);
        probleme_detecte = true;
        message = '';
        return false;
    }
    else
    {
        return true;
    }
}

function urlImg()
{	
    var settings = {
					'width' : 250,
					'height' : 250
    };
    marvinSketcherInstance.exportStructure("jpeg", settings).then(function(source) {
                        //alert(source);
						//<?php $_SESSION['molURL'] = source ; ?>	
                       // alert(<?php echo $_SESSION['molURL']; ?>);
    }, function(error) {
                    //alert("Image export failed:"+error);	
    });	

    
}

function traiteSimilarite()
{	
    //alert(document.getElementById("optionsRadios1"));
    if(document.getElementById('optionsRadios1').checked)
    {
		var fp = document.getElementById('optionsRadios1').value;
	} 
    else 
    { 
		var fp = document.getElementById('optionsRadios2').value;
	}
    
	//var nbSortie = document.getElementById('nbSortie').value;
    var nbSortie = 20;
    
    document.getElementById("testloader").style.visibility="visible";
   // alert(document.getElementById("testloader").style.visibility);
       
    xmlHttp = GetXmlHttpObject();
    if (xmlHttp == null)
    {
        alert("Votre navigateur ne supporte pas les requêtes HTTP.");
        return false;
    } 
        
    var url="recuperationDonneesSimilarite.php";
    
    xmlHttp.onreadystatechange = function (){
        if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
        {
            eval(xmlHttp.responseText);
            document.getElementById("testloader").style.visibility="hidden";
            //alert(xmlHttp.responseText);
            //alert(mol);
            if (xmlHttp.responseText != '1') 
            {
                document.getElementById("testloader").innerHTML= "<img src='img/errorRouge_bis_w25.png' class='imgTexte'/>&nbsp;&nbsp;<p id='errorMolSimilarite'>An Error occured. Please try again or contact us at info@cdithem.fr to let us know.</p>";
            }
            else
            {
               // alert("ok");
                window.location.assign("afficheResults.php");
                
            }
                
        }
    }
    
    xmlHttp.open("POST", url, true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
    
    varSend="fp="+fp+"&nbSortie="+nbSortie;
    var settings = {
        'width' : 350,
        'height' : 350
    };
    
    var marvinSketcherInstance;
        MarvinJSUtil.getEditor('#sketch').then(function(sketcherInstance) { 
				marvinSketcherInstance = sketcherInstance;
                marvinSketcherInstance.exportStructure("jpeg", settings).then(function(source) {
            varSend=varSend+"&source="+source;
            //alert(varSend);
            xmlHttp.send(varSend);
						
    }, function(error) {
            alert("Image export failed:"+error);	
    });
        }, function(error) {
        alert("Loading of the sketcher failed"+error);
        });
    
    
    //source = marvin.ImageExporter.molToDataUrl(mol,"image/png", settings);
   // varSend=varSend+"&source="+source;
    //xmlHttp.send(varSend);

    //document.getElementById("testloader").style.visibility="visible";
    return true;


    
}

function recupMolUrl(molInput)
{
    var settings = {
        'width' : 350,
        'height' : 350
    };
    return marvin.ImageExporter.molToDataUrl(molInput,"image/jpeg", settings);
}
//recuperation de la molecule sous forme de smile a partir de marvinSketch
function recupMol()
{	
    //alert("here");

    /*applet = document.MSketch;
    if(!applet.isEmpty())
    {
        //alert(applet.getMol('smiles'));
        //alert(document.getElementById("infos_1").innerHTML);
        smile = applet.getMol('smiles');
        indexPoint = smile.indexOf('.');
        
        if (indexPoint >= 0)
        {
            probleme_detecte = true;
            message = 'Do not enter more than one molecule !';
            alert(message);
        }
        else
        {
            document.getElementById("infos_1").innerHTML = applet.getMol('smiles');
            //alert(document.getElementById("infos_1").innerHTML);
        
            document.getElementById("boutonSearchSimilarite").disabled = false;            
         }
    }*/
   	
    var marvinSketcherInstance;
    MarvinJSUtil.getEditor('#sketch').then(function(sketcherInstance) { 
        marvinSketcherInstance = sketcherInstance;		
        marvinSketcherInstance.exportStructure("mol").then(function(source)
        {
            mol = source;
            
            // convertir la molecule en smiles        
            xmlHttp = GetXmlHttpObject();
            if (xmlHttp == null)
            {
                alert("Votre navigateur ne supporte pas les requêtes HTTP.");
                return false;
            } 
            var url="convertMolSimilarite.php";
            document.getElementById('statusMol').innerHTML = "<img src='img/ajax-loader-similarite.gif' />&nbsp;&nbsp;<p id='enCoursMolSimilarite'> Importing molecule.</p>";
        
            xmlHttp.onreadystatechange = function (){
                if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
                {
                    eval(xmlHttp.responseText);
                
                    if (xmlHttp.responseText != "Error")
                    {
                        //document.getElementById('statusMol').innerHTML = "<img src='img/glyphicons-199-ok_vert.png' width=20px/> &nbsp; Molecule correctly imported.";
                        document.getElementById('statusMol').innerHTML = "<img src='img/okVert_bis_w25.png' class='imgwa'/>&nbsp;&nbsp;<p id='okMolSimilarite'>Molecule correctly imported.</p>";
                        //document.getElementById('statusMol').innerHTML = "<i class='icon-ok-circle icon-white'></i>	 &nbsp; Molecule correctly imported.";
                        document.getElementById("boutonSearchSimilarite").disabled = false;
                
                    }
                    else
                    {
                        document.getElementById('statusMol').innerHTML = "<img src='img/errorRouge_bis_w25.png' class='imgTexte'/>&nbsp;&nbsp;<p id='errorMolSimilarite'>An Error occured while importing your molecule. PLease check you only have ONE molecule.</p>";
                    }                
                
                }
            }
    
            xmlHttp.open("POST", url, true);
            xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
            action = "mrv";
            varSend="mol="+mol+"&action="+action;
        
            xmlHttp.send(varSend);
            return true;
                    
        }, function(error) {
            alert("Molecule export failed:"+error);	
        });
    }, function(error) {
        alert("Loading of the sketcher failed"+error);
    });
    
}

function execAction(mol,action)
{
    if (mol)
    {    
        
        indexPoint = mol.indexOf('.');
        
        if (indexPoint >= 0)
        {
            probleme_detecte = true;
            message = 'Do not enter more than one molecule !';
            alert(message);
        }
        else 
        {
            document.getElementById("enCoursSmilesSimilarite").style.visibility="visible";
            xmlHttp = GetXmlHttpObject();
            if (xmlHttp == null)
            {
                alert("Votre navigateur ne supporte pas les requêtes HTTP.");
                return false;
            } 
            var url="convertMolSimilarite.php";
    
            xmlHttp.onreadystatechange = function (){
                if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
                    eval(xmlHttp.responseText);
                    document.getElementById("enCoursSmilesSimilarite").style.visibility="hidden";
            }
    
            xmlHttp.open("POST", url, true);
            xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
            varSend="mol="+mol+"&action="+action;
    
            xmlHttp.send(varSend);
            return true;
        }
    }
    else
    {
        var marvinSketcherInstance;
        MarvinJSUtil.getEditor('#sketch').then(function(sketcherInstance) { 
				marvinSketcherInstance = sketcherInstance;
                marvinSketcherInstance.clear();
        }, function(error) {
        alert("Loading of the sketcher failed"+error);
        });
    }

} 

function inscriptionNewsLetter()
{
    mail = document.getElementById("emailNews").value;
    var retour="";
    var retourMsg="";
        
    if (mail)
    {    
        xmlHttp = GetXmlHttpObject();
        if (xmlHttp == null)
        {
            alert("Votre navigateur ne supporte pas les requêtes HTTP.");
            return false;
        } 
        var url="inscNewsLetter.php";
    
        xmlHttp.onreadystatechange = function (){
            if (xmlHttp.readyState == 4 || xmlHttp.readyState == "complete")
                eval(xmlHttp.responseText);
                if(retour == "ok"){
                    document.getElementById('statusNews').style.visibility="visible";       
                    document.getElementById("emailNews").value="";
                } 
                else if (retour == "errorForm"){
                    alert(retourMsg);
                } 
              
        }
       
    
        xmlHttp.open("POST", url, true);
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
        varSend="mail="+mail;
    
        xmlHttp.send(varSend);
        return true;
      
    }
    else
    {
       alert("Error : no email adress. Please fill the email adress field.");
        
    }

} 


function GetXmlHttpObject()
{ 
    var objXMLHttp = null;
    if (window.XMLHttpRequest)
        objXMLHttp=new XMLHttpRequest();
    else if (window.ActiveXObject)
        objXMLHttp=new ActiveXObject("Microsoft.XMLHTTP");
    return objXMLHttp;
} 




 

function log10(val) 
{
  return (Math.log(val) / Math.LN10);
} 

function convertActivity(id)
{ 
    //recuperer le tableau de resultats et acceder au 2 cellules concernees
    
    var arrayLignes = $('#results').rows; //l'array est stocké dans une variable
    var longueur = arrayLignes.length;
    var i=1; //on définit un incrémenteur qui représentera la clé
   // var indType=0;
    //var indAct=0;
   
    if (id == "results")
    {
        var indType=5;
        var indAct=6;
    }
    else
    {
        var indType=6;
        var indAct=7;
    }
    
    if (arrayLignes[1].cells[indType].innerHTML.indexOf('p') != -1)
    {
        var convertVers="IC";
        
    }
    else
    {
        var convertVers="pIC";        
        
    }
       
    while(i<longueur)
    {
        if(convertVers == "IC")
        {
            arrayLignes[i].cells[indType].innerHTML = arrayLignes[i].cells[indType].innerHTML.substring(1);
            arrayLignes[i].cells[indAct].innerHTML = Number((Math.pow(10, -arrayLignes[i].cells[indAct].innerHTML)*Math.pow(10,6)).toFixed(2));
        }
        else if (convertVers == "pIC")
        {
            arrayLignes[i].cells[indType].innerHTML = "p"+arrayLignes[i].cells[indType].innerHTML;
            arrayLignes[i].cells[indAct].innerHTML = -1*Number((log10(arrayLignes[i].cells[indAct].innerHTML*Math.pow(10,-6))).toFixed(2));
        }
        
        i++;
    }

} 


function afficheLegendeACP()
{
	if (document.getElementById("legendeACP").style.visibility != "visible")
    {
    	document.getElementById("legendeACP").style.visibility="visible";
        document.getElementById("LegendePhysChem").style.background="url(img/hideCaption.png)";
    }
    else
    {
        document.getElementById("legendeACP").style.visibility="hidden";
        document.getElementById("LegendePhysChem").style.background="url(img/showCaption.png)";
    }
}

function afficheLegendeLE()
{
	if (document.getElementById("legendeLE").style.visibility != "visible")
    {
    	document.getElementById("legendeLE").style.visibility="visible";
        document.getElementById("LegendePharmaco").style.background="url(img/hideCaption.png)";
    }
    else
    {
        document.getElementById("legendeLE").style.visibility="hidden";
        document.getElementById("LegendePharmaco").style.background="url(img/showCaption.png)";
    }
}


function cacheLegende()
{
    document.getElementById("legendeACP").style.visibility="hidden";
    document.getElementById("LegendePhysChem").style.backgroundImage="url(../img/showCaption.png)";

    
}  

function goBack()
{
  window.history.back()
} 

function activeChamp(formulaire, champs)
{
	if(champs == "lipinski")
	{
		//if (formulaire.elements[champs].checked)
		//{
			//formulaire.elements["MW"].value = "500";
			//formulaire.elements["AlogP"].value = "5";
			//formulaire.elements["NbHBondDon"].value = "5";
			//formulaire.elements["NbHBondAcc"].value = "10";
		//}
		//else
		//{
		//	formulaire.elements["MW"].value = "-";
		//	formulaire.elements["AlogP"].value = "-";
		//	formulaire.elements["NbHBondDon"].value = "-";
		//	formulaire.elements["NbHBondAcc"].value = "-";
		//}	
			formulaire.elements["MW"].disabled = formulaire.elements[champs].checked;
			formulaire.elements["AlogP"].disabled = formulaire.elements[champs].checked;
			formulaire.elements["NbHBondDon"].disabled = formulaire.elements[champs].checked;
			formulaire.elements["NbHBondAcc"].disabled = formulaire.elements[champs].checked;
			formulaire.elements["veber"].disabled = formulaire.elements[champs].checked;
			formulaire.elements["pfizer"].disabled = formulaire.elements[champs].checked;
	}
	else if(champs == "veber")
	{
		//if (formulaire.elements[champs].checked)
		//{
		//	formulaire.elements["nROT"].value = "11";
		//	formulaire.elements["TPSA"].value = "140";
		//}
		//else
		//{
		//	formulaire.elements["nROT"].value = "-";
		//	formulaire.elements["TPSA"].value = "-";
		//}	

		formulaire.elements["nROT"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["TPSA"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["NbHBondDon"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["NbHBondAcc"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["pfizer"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["lipinski"].disabled = formulaire.elements[champs].checked;
	}
	else if(champs == "pfizer")
	{
		//if (formulaire.elements[champs].checked)
		//{
		//	formulaire.elements["AlogP"].value = "3";
	//		formulaire.elements["TPSA"].value = "75";
	//	}
	//	else
	//	{
	//		formulaire.elements["AlogP"].value = "-";
	//		formulaire.elements["TPSA"].value = "-";
	//	}
		
		formulaire.elements["AlogP"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["TPSA"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["veber"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["lipinski"].disabled = formulaire.elements[champs].checked;
	}
    else if(champs == "selectDrugOnly")
	{
		//if (formulaire.elements[champs].checked)
		//{
		//	formulaire.elements["AlogP"].value = "3";
	//		formulaire.elements["TPSA"].value = "75";
	//	}
	//	else
	//	{
	//		formulaire.elements["AlogP"].value = "-";
	//		formulaire.elements["TPSA"].value = "-";
	//	}
    
        // desactive les filtres
        
        formulaire.elements["pfizer"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["lipinski"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["veber"].disabled = formulaire.elements[champs].checked;
        
        // desactive les descripteurs
		formulaire.elements["nROT"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["TPSA"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["NbHBondDon"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["NbHBondAcc"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["AlogP"].disabled = formulaire.elements[champs].checked;        
        formulaire.elements["MW"].disabled = formulaire.elements[champs].checked;
		formulaire.elements["AlogP"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["nArSR"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["FSP3"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["nChiralCenters"].disabled = formulaire.elements[champs].checked;
        
        // desactive les tests, type de tests et fourchette activite
        formulaire.elements["pic50"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["testName"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["activityMin"].disabled = formulaire.elements[champs].checked;
        formulaire.elements["activityMax"].disabled = formulaire.elements[champs].checked;
    
    }


}

//verification du contenu des champs pour le formulaire PPI-HitProfiler
function verif_champsPPi(mail, nom, question, youAre, codeSpam)
{	
	var probleme_detecte = false;
	var message = '';
	
	if (mail == "")
	{
		probleme_detecte = true;
		message = 'The email field is empty !';
	}
	else
	{
		indexAroba = mail.indexOf('@');
		indexPoint = mail.indexOf('.');
		if ((indexAroba < 0) || (indexPoint < 0))
		{
			probleme_detecte = true;
			message = 'The email field is not valid !';
		}
	}
	
	if (nom == "")
	{
		probleme_detecte = true;
		message = 'The name field is empty !';
	}
	
	if (codeSpam == "")
	{
		probleme_detecte = true;
		message = 'The captcha field is empty !';
	}
	
	if ((question == "") || ( question=="Enter here your request details"))
	{
		probleme_detecte = true;
		message = 'The request field is empty !';
	}	
	
	if ((!youAre[0].checked) && (!youAre[1].checked))
	{
		probleme_detecte = true;
		message = 'the "you are" field is empty !';
	}
	
	if (probleme_detecte)
	{
		alert(message);
		probleme_detecte = true;
		message = '';
		return false;
	}
	else
	{
		return true;
	}
}

//verification du contenu des champs pour le formulaire info
function verif_champs(mail, nom, question, youAre, purpose, codeSpam)
{
	
	var probleme_detecte = false;
	var message = '';
	
	if (mail == "")
	{
		probleme_detecte = true;
		message = 'The email field is empty !';
	}
	else
	{
		indexAroba = mail.indexOf('@');
		indexPoint = mail.indexOf('.');
		if ((indexAroba < 0) || (indexPoint < 0))
		{
			probleme_detecte = true;
			message = 'The email field is not valid !';
		}
	}
	
	if (nom == "")
	{
		probleme_detecte = true;
		message = 'The name field is empty !';
	}
	
	if (codeSpam == "")
	{
		probleme_detecte = true;
		message = 'The captcha field is empty !';
	}
	
	if ((question == "") || ( question=="Enter here your request details"))
	{
		probleme_detecte = true;
		message = 'The request field is empty !';
	}	
	
	if ((!youAre[0].checked) && (!youAre[1].checked))
	{
		probleme_detecte = true;
		message = 'the "you are" field is empty !';
	}
	
	if ((!purpose[0].checked) && (!purpose[1].checked) && (!purpose[2].checked))
	{
		probleme_detecte = true;
		message = 'the "purpose" field is empty !';
	}
	
	if (probleme_detecte)
	{
		alert(message);
		probleme_detecte = true;
		message = '';
		return false;
	}
	else
	{
		return true;
	}
}