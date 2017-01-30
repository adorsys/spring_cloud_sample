#-----------------------------------------------------------------------------
# local development
#-----------------------------------------------------------------------------

build  : ## run maven build for all projects
build:
	@BASE_DIR=`pwd` && for dir in */; do cd $$BASE_DIR/$$dir && mvn package; done && cd $$BASE_DIR

docker : ## Dockerized all projects and start them
docker:
	@docker-compose build && docker-compose up

#-----------------------------------------------------------------------------
# supporting targets
#-----------------------------------------------------------------------------

help   : ## Show this help.
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

.PHONY : build help
