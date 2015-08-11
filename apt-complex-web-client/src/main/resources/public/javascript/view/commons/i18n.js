(function() {
    var app = angular.module("app-i18n",
        [
            "pascalprecht.translate",
            "angularMoment"
        ]
    );

    app.run(
        function(amMoment) {
            amMoment.changeLocale("en");
        }
    );

    app.constant("APP_LOCALE",
        {
            EN_US: "en_US",
            PT_BR: "pt_BR"
        }
    );

    app.config(
        [
            "$translateProvider",
            "APP_LOCALE",
            function($translateProvider, APP_LOCALE) {
                $translateProvider.translations(APP_LOCALE.EN_US, {
                    PAGE_TITLE: 'Apt. Complex Manager',

                    MENU_SUMMARY: 'Summary',
                    MENU_INCOMES: 'Incomes',
                    MENU_FEES: 'Fees',
                    MENU_BANKS: 'Banks',
                    MENU_EXPENSES: 'Expenses',
                    MENU_PERSONNEL: 'Personnel',

                    MENU_ABOUT: 'About...',

                    MENU_ENGLISH: 'English (US)',
                    MENU_PORTUGUESE: 'Portuguese (BR)',

                    SUMMARY_TITLE: "Summary",
                    SUMMARY_INCOMES: "Incomes",
                    SUMMARY_EXPENSES: 'Expenses',

                    ABOUT_TITLE: 'Welcome...',
                    ABOUT_SUBTITLE: '... To <b>Apartment Complex</b> Manager',
                    ABOUT_PARAGRAPH_1: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec aliquam elit sit amet risus tempus condimentum. Nam id aliquam ligula, non tincidunt erat. Vivamus nec luctus lorem, sed elementum tortor. Cras accumsan ligula magna, sed cursus quam pellentesque tempus. Donec consequat lorem ac mauris pellentesque viverra. Suspendisse ultricies est in libero sagittis, sed lacinia mauris malesuada. Integer accumsan malesuada mattis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae.',
                    ABOUT_PARAGRAPH_2: 'Aliquam bibendum enim odio, ornare egestas velit placerat at. Fusce fermentum egestas metus non posuere. Morbi porta pharetra neque, ut eleifend dui. In nisl nunc, lobortis quis placerat at, vehicula sit amet leo. Proin scelerisque, mi fermentum varius tempus, nulla lorem porttitor felis, non suscipit tortor tellus id neque. Aenean eu imperdiet mauris. Cras eu fringilla nisl. In malesuada fringilla rhoncus. Sed in mauris nec enim lacinia tristique. Nam id lacus est. Praesent ipsum arcu, tempor vitae posuere in, tincidunt eu quam. In interdum sapien hendrerit aliquet bibendum. Donec id felis eget ex eleifend tempus. Nullam eget arcu orci.',
                    ABOUT_LEARN_MORE: 'Learn more',

                    REMOTE_API_BALANCE_SUMMARY_RETRIEVING: 'Retrieving summary balance for ',
                    REMOTE_API_BALANCE_SUMMARY_RETRIEVED: 'Summary balance retrieved',
                    REMOTE_API_BALANCE_SUMMARY_ERROR_RETRIEVING: 'Error retrieving summary balance',

                    GENERAL_NO_DATA_TITLE: "Uh oh!",
                    GENERAL_NO_DATA_MESSAGE: "Seems that there is no data registered for this period. No problem... Just create a new monthly balance or try another period."
                });
                $translateProvider.translations(APP_LOCALE.PT_BR, {
                    PAGE_TITLE: 'Gerenciador de Condomínio',
                    MENU_SUMMARY: 'Resumo',
                    MENU_INCOMES: 'Créditos',
                    MENU_FEES: 'Taxas',
                    MENU_BANKS: 'Bancos',
                    MENU_EXPENSES: 'Gastos',
                    MENU_PERSONNEL: 'Pessoal',

                    MENU_ABOUT: 'Sobre...',

                    MENU_ENGLISH: 'Inglês (EUA)',
                    MENU_PORTUGUESE: 'Português (BR)',

                    SUMMARY_TITLE: "Resumo",
                    SUMMARY_INCOMES: "Créditos",
                    SUMMARY_EXPENSES: 'Gastos',

                    ABOUT_TITLE: 'Bem vindo...',
                    ABOUT_SUBTITLE: '... Ao gerenciador de <b>Condomínio</b>',
                    ABOUT_PARAGRAPH_1: 'Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras laoreet sem fermentum libero interdum, placerat mollis est suscipit. Proin mollis finibus porttitor. Fusce feugiat nibh non massa vehicula, id pharetra arcu imperdiet. Cras vitae urna vel odio faucibus tristique non quis nisl. Nullam eget nunc et elit venenatis porta vitae et eros. Nullam metus enim, pretium ut vulputate eu, maximus nec urna. Nunc elementum magna arcu, vel blandit tellus vehicula rutrum. Nullam nec lacinia nulla.',
                    ABOUT_PARAGRAPH_2: 'Donec a efficitur ipsum. Praesent nec sodales purus. Curabitur justo quam, luctus non malesuada et, dapibus tristique libero. Nunc tristique justo eu sapien fermentum bibendum. Maecenas scelerisque vitae orci id semper. Aenean ultrices dui et elementum tincidunt. Phasellus lacinia arcu erat, eu tempor orci dapibus eget. Nunc elementum quam et odio porttitor, varius ultrices eros blandit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam diam lacus, ornare vitae risus vitae, tempor ultrices enim.',
                    ABOUT_LEARN_MORE: 'Veja mais',

                    REMOTE_API_BALANCE_SUMMARY_RETRIEVING: 'Recebendo o resumo do balancete de ',
                    REMOTE_API_BALANCE_SUMMARY_RETRIEVED: 'Resumo do balancete recebido',
                    REMOTE_API_BALANCE_SUMMARY_ERROR_RETRIEVING: 'Erro ao receber o resumo do balancete',

                    GENERAL_NO_DATA_TITLE: "Oh oh!",
                    GENERAL_NO_DATA_MESSAGE: "Parece que não há registros para este período. Sem problema... Apenas registre um novo balancete mensal ou escolha um outro período."
                });

                $translateProvider.preferredLanguage(APP_LOCALE.EN_US);
            }
        ]
    );
})();