class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'ociTest', action: 'index')
        "500"(view: '/error')
    }
}
