folder=app/src/main/graphql/un.eagle.elsa
rm $folder/schema.json
apollo schema:download --endpoint http://35.209.23.230:8081/graphql $folder/schema.json
tree $folder
