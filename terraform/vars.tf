# Digital ocean
variable "do_token" {
  type = string
}

# Cluster
variable "application_name" {
  type    = string
  default = "poly-yard-app"
}

variable "k8s_region" {
  type    = string
  default = "lon1"
}
variable "k8s_node_count" {
  type    = number
  default = 1
}

variable "k8s_size" {
  type    = string
  default = "s-2vcpu-2gb"
}

variable "k8s_version" {
  type    = string
  default = "1.29.1-do.0"
}