$undertow
    .setDefault('template_type', 'thymeleaf')
    .onGet('/testTemplate', {template: 'template.txt'},  function() {
        return {data: 'Some Data'};
    });