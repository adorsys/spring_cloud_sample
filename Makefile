# Example Makefile for docker builds
# See http://blog.dixo.net/2015/07/building-docker-containers-with-make-on-coreos/

DEPS = $(shell find cart/src product/src -type f -name '[!.]*' -print)

#-----------------------------------------------------------------------------
# default target
#-----------------------------------------------------------------------------
all   		: ## Build the containers - this is the default action
all: jar image
#-----------------------------------------------------------------------------
# build container
#-----------------------------------------------------------------------------
image 		: ## Build the containers
image:
	docker-compose build
#-----------------------------------------------------------------------------
# local development
#-----------------------------------------------------------------------------
jar	        : ## Build all jars
jar: $(DEPS)
	@cd cart; mvn clean package;
	@cd product; mvn clean package;
#-----------------------------------------------------------------------------
# supporting targets
#-----------------------------------------------------------------------------
help  		: ## Show this help.
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'
.PHONY : all help