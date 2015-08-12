import pystache
import json
import sys

with open('langs.json', 'r') as f:
    langs_list = [l for l in reversed(json.loads(f.read()))]
    langs_mapper = {lang['locale'].replace('-', '_'): lang['crowdin_code'] for lang in langs_list}

def get_lang(lang):
    return [l for l in langs_list if l['crowdin_code'] == lang][0]

langs_args = sorted(sys.argv[1].split(',') + ['en_US'])
used_langs = [get_lang(langs_mapper[lang]) for lang in langs_args]

with open('etc/home.html', 'r') as f:
    tpl = f.read()

with open('dist/index.html', 'w') as f:
    f.write(pystache.render(tpl, dict(langs=used_langs)))
