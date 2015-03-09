import requests
import os
import json

comment_tpl = """
A preview for this pull request is available at %s/%s/index.html.

_Since the preview always changes, please link to [this comment](%s), not to the direct url to the preview._
"""

def mk_comment(commit, comment):
    return {'body': comment_tpl % (rawgit, commit, comment)}

pr = os.environ.get('TRAVIS_PULL_REQUEST')
token = os.environ.get('GH_TOKEN')

rawgit = 'https://cdn.rawgit.com/Spongy/SpongeDocs-PRs'
repo = 'https://api.github.com/repos/SpongePowered/SpongeDocs'
pr_repo = 'https://api.github.com/repos/Spongy/SpongeDocs-PRs'

commit = requests.get('%s/branches/%s' % (pr_repo, pr)).json()['commit']['sha'][:8]

comments = requests.get('%s/issues/%s/comments' % (repo, pr), auth=('x-oauth-basic', token)).json()
spongy_comments = [comment for comment in comments if comment['user']['login'] == 'Spongy']

if spongy_comments:
    comment = spongy_comments[0]
    comment_id = comment['id']
    comment_url = comment['html_url']
    requests.patch(
        '%s/issues/comments/%s' % (repo, comment_id),
        auth=('x-oauth-basic', token),
        data=json.dumps(mk_comment(commit, comment_url)))
else:
    payload = {'body': 'Setting up PR reference, please wait...'}
    comment = requests.post(
        '%s/issues/%s/comments' % (repo, pr),
        auth=('x-oauth-basic', token),
        data=json.dumps(payload)).json()
    comment_id = comment['id']
    comment_url = comment['html_url']
    requests.patch(
        '%s/issues/comments/%s' % (repo, comment_id),
        auth=('x-oauth-basic', token),
        data=json.dumps(mk_comment(commit, comment_url)))
