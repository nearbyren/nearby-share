#!/bin/sh
#�����ύ����������ΪOLD_EMAIL���û���ΪOLD_NAME��Ϊ�µ��û������µ�����

git filter-branch --env-filter '
OLD_NAME="wangcunrong@ejiayou.com"
CORRECT_NAME="lr"

if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_NAME" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' -f --tag-name-filter cat -- --branches --tags    #-fΪǿ�и���
#ȡ�������#ע�ͣ����Զ�ǿ�����������޸ĵ�����֧
#git push origin master --force