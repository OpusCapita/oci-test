const fsPromises = require("fs").promises;
const readProperties = require('properties-reader');

const createFormManger = () => {
  return {
    async getFormsNames(filePath) {
      try {
        const result = await fsPromises.readFile(filePath);
        return JSON.parse(result).forms.map(form => form.name);
      } catch (e) {
        throw e;
      }
    },

    async getForm(filePath, formName) {
      try {
        const result = await fsPromises.readFile(filePath);
        const form = JSON.parse(result).forms.find(form => form.name === formName);
        if (!form) {
          throw new Error(`Form witn name "${formName}" not found`);
        }
        return form;
      } catch (e) {
        throw e;
      }
    },

    async editForm(filePath, formName, fields) {
      try {
        const result = await fsPromises.readFile(filePath);
        const forms = JSON.parse(result).forms;
        const form = forms.find(form => form.name === formName);
        form.fields = [...fields];
        return this.saveForms(filePath, forms);
      } catch (e) {
        throw e;
      }
    },

    async saveForms(filePath, forms) {
      return fsPromises.writeFile(filePath, JSON.stringify({ forms }, null, 2));
    },

    createResultForm(name, body) {
      const items = [];
      Object.entries(body).map(property => {
        if (Array.isArray(property[1])) {
          property[1].forEach((value, i) => {
            if (!Array.isArray(items[i])) {
              items[i] = [];
            }
            items[i].push({ id: `${property[0]}[${i + 1}]`, value });
          });
        }
      });
      return { name, items }
    },

    getFormFromProperties(filePath, name) {
      return {
        name,
        fields: Object.entries(readProperties(filePath)._properties).map(property => (
          { id: property[0], value: property[1], })),
      };
    },

    async getFormXML(filePath, name) {
      try {
        const xml = await fsPromises.readFile(filePath);
        return { name, xml };
      }
      catch (e) {
        throw e;
      }
    },

    setSetupConfigs(starterConfigPath, configPath, setupConfig) {
      return fsPromises.readFile(starterConfigPath).
        then(formsString => {
          formsString = formsString.toString();
          Object.entries(setupConfig).forEach(([name, value]) => {
            formsString = formsString.replace(new RegExp(name, 'g'), value);
          })
          return formsString;
        }).
        then(formsString => fsPromises.writeFile(configPath, formsString))
    }
  }
}

module.exports = createFormManger;
