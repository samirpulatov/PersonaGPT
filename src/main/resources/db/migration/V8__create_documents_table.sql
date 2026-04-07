create table documents
(
    id                bigint auto_increment primary key,
    user_id           bigint not null,
    original_filename varchar(255) not null,
    storage_path      varchar(500) not null,
    mime_type         varchar(255) not null,
    file_size         bigint not null,
    status            varchar(50) not null,
    raw_text          longtext null,
    parsed_json       json null,
    created_at        datetime not null,
    updated_at        datetime null,
    parsed_at         datetime null,

    constraint documents_users_id_fk
        foreign key (user_id) references users (id)
);